package com.scarlatti

import org.w3c.dom.Element

class ElementHandler {

    Element thisElement
    Closure traverseElement

    ElementHandler(Element thisElement, Closure traverseElement) {
        this.thisElement = thisElement
        this.traverseElement = traverseElement
        this.traverseElement.delegate = this
        this.traverseElement.resolveStrategy = Closure.DELEGATE_FIRST  // TODO can I delegate OWNER_FIRST, but have the parent ElementHandler ignore method missing calls while a child is working?
    }

    /**
     * Decide what to do with this element
     * @param traverseElement the closure that will traverse this element
     * @return the element traversed
     */
    Element handleElement() {
        if (traverseElement != null) {
            String text = traverseElement()
//            println "traverse finished for delegate ${thisElement.nodeName}"

            if (text != null)
                addTextValue(text)
        }

        return thisElement
    }

    // TODO teach this metaclass about those methods
    // overrides are OK even across multiple classes.
    // we would have Spring managed creators...
    // OR we could use a single instance, probably better in the case
    // of hundreds of documents...
    def methodMissing(String name, args) {
//        println "called Delegate ${this.thisElement.nodeName} with method $name"

        // add the method

        handleTag(name, args)
    }

    def propertyMissing(String name) {
//        println "called Delegate ${thisElement.nodeName} for property $name"

        handleTag(name)
    }

    void createElement(String name) {
        addEmptyNodeWithOneTag(name)
    }

    void createElement(String name, String text) {
        addElementWithText(name, text)
    }

    void createElement(String name, Closure traverseElement) {
        addNewElement(name, traverseElement)
    }

    void handleTag(String name, Object[] args = []) {
        if (args.length == 0)
            addEmptyNodeWithOneTag(name)

        else if (args[0] instanceof String)
            addElementWithText(name, (String) args[0])

        else if (args[0] instanceof Closure)
            addNewElement(name, (Closure) args[0])
    }

    void addEmptyNodeWithOneTag(String name) {
        Element element = thisElement.ownerDocument.createElement(name)
        thisElement.appendChild(element)
    }

    // TODO can I set an empty pair of tags?
    void addElementWithText(String name, String text) {
        Element element = thisElement.ownerDocument.createElement(name)
        element.setTextContent(text)
        thisElement.appendChild(element)
    }

    void addTextValue(String text) {
        thisElement.setTextContent(text)
    }

    void addNewElement(String name, Closure traverseElement) {
        // recursive call
        Element element = thisElement.ownerDocument.createElement(name)
        thisElement.appendChild(element)

        ElementHandler handler = new ElementHandler(element, traverseElement)
        handler.handleElement()

        // TODO how to tell the difference between calling this when it will have nested elements, and when it will be a single element?  Could use child.hasChildren?
    }
}

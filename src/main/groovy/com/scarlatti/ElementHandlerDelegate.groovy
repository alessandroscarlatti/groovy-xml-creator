package com.scarlatti

/**
 * Created by pc on 10/14/2017.
 */
class ElementHandlerDelegate {

    ElementHandler handler

    ElementHandlerDelegate(ElementHandler elementHandler) {
        this.handler = elementHandler
    }

    def methodMissing(String name, args) {
        println "called Delegate ${handler.thisNode.nodeName} with method $name"

        if (args.length == 0)
            handler.addEmptyNodeWithOneTag(name)

        else if (args[0] instanceof String)
            handler.addElementWithText(name, (String) args[0])

        else if (args[0] instanceof Closure)
            handler.addNewElement(name, (Closure) args[0])

    }

    def propertyMissing(String name) {
        println "called Delegate ${handler.thisNode.nodeName} for property $name"

        handler.addEmptyNodeWithOneTag(name)
    }
}

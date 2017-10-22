package com.scarlatti.attempt1

class ElementHandlerStax {

    XMLBuilderStax builder

    ElementHandlerStax(XMLBuilderStax builder) {
        this.builder = builder
    }

    def methodMissing(String name, args) {
        builder.handleTag(name, args)
    }

    def propertyMissing(String name) {
        builder.handleTag(name, null)
    }

    void createElement(String name) {
        builder.addEmptyNodeWithOneTag(name)
    }

    void createElement(String name, String text) {
        builder.addElementWithText(name, text)
    }

    void createElement(String name, Closure traverseElement) {
        builder.addNewElement(name, traverseElement)
    }
}

package com.scarlatti.attempt4

class ElementHandler {

    com.scarlatti.attempt4.XMLBuilder builder

    ElementHandler(com.scarlatti.attempt4.XMLBuilder builder) {
        this.builder = builder
    }

    def methodMissing(String name, args) {
        builder.handleTag(name, args)
    }

    def propertyMissing(String name) {
        builder.handleTag(name, [])
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

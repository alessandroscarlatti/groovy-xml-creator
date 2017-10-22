package com.scarlatti.attempt4

class ElementHandlerStax {

    XMLBuilderStax builder

    ElementHandlerStax instance

    ElementHandlerStax(XMLBuilderStax builder) {
        this.builder = builder
        instance = this
    }

    void addMethodMissingAndPropertyMissing() {
        instance.metaClass.methodMissing << { String name, args ->

            instance.metaClass."$name" << {
                delegate.builder.handleTag(name, null)
            } << {String s ->
                delegate.builder.handleTag(name, s)
            } <<{ Closure c ->
                delegate.builder.handleTag(name, c)
            }

            delegate.builder.handleTag(name, args)
        }

        instance.metaClass.propertyMissing = { String name ->
            delegate.builder.handleTag(name, null)
        }
    }

    void removeMethodMissingAndPropertyMissing() {
        instance.metaClass.methodMissing << { String name, args ->
            throw new MissingMethodException(name, this.class, args)
        }

        instance.metaClass.propertyMissing = { String name ->
            delegate.builder.handleTag(name, null)
        }
    }

//    def methodMissing(String name, args) {
//        builder.handleTag(name, args)
//    }

//    def propertyMissing(String name) {
//        builder.handleTag(name, null)
//    }

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

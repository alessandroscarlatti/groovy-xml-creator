package com.scarlatti.attempt3

class WizardElementHandlerStax {

    XMLWizard builder
    Object owner

    Map<String, String> insidePropsMap = new HashMap<>();
    private String isInsideProp;

    WizardElementHandlerStax(XMLWizard builder) {
        this.builder = builder
    }

    // TODO
    // if a method or property is missing:
    // the first time try to look it up in the owner first.
    // this will take longer, but will be faster later.
    // if the method is found, invoke it
    // otherwise, return control to the delegate.

    def methodMissing(String name, args) {
//        println "missing method $name"
        WizardElementHandlerStax.metaClass."$name" = { cArgs ->
            builder.handleTag(name, args)
        }

        builder.handleMissingMethod(name, args, owner)
    }

    def propertyMissing(String name) {
//        println "missing property $name"

        if (isInsideProp != null) {

            return handleKnownProp(name)

        } else {
            return handleUnknownProp(name)
        }
    }

    def handleKnownProp(String name) {
        if (isInsideProp == "i") {
            builder.handleTag(name, null)
        } else {
            return owner[name]
        }
    }

    def handleUnknownProp(String name) {
        try {
            def val = owner[name]

            // if it was successful, return the value and cache this method
            insidePropsMap.put(name, "o")
            return val
        } catch (Exception e) {
            insidePropsMap.put(name, "i")
            builder.handleTag(name, null)
        }
    }

    void setSecondTime() {
        // reassign the methodmissing and propertymissing methods

        this.metaClass = WizardElementHandlerStax.metaClass


//        this.metaClass.methodMissing = { String name, args ->
//            builder.handleKnownMethod(name, args)
//        }
//
//        this.metaClass.propertyMissing = { String name ->
//            handleKnownProp(name)
//        }
    }

    def invokeMethodOnOwner(String name, Object args) {
        return owner.invokeMethod(name, args)
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

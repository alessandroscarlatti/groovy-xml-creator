package com.scarlatti.validation

import org.custommonkey.xmlunit.XMLUnit
import org.custommonkey.xmlunit.XpathEngine
import org.w3c.dom.Document

/**
 * ~     _____                                    __
 * ~    (, /  |  /)                /)         (__/  )      /)        ,
 * ~      /---| // _ _  _  _ __  _(/ __ ___     / _ _  __ // _ _/_/_
 * ~   ) /    |(/_(//_)/_)(_(/ ((_(_/ ((_)   ) / (_(_(/ ((/_(_((_(__(_
 * ~  (_/                                   (_/
 * ~  Thursday, 11/2/2017
 */
class XPathHandler {

    private XpathEngine xpathEngine;
    private Document document;

    XPathHandler(String xml) {

        // build the internal xpath engine
        xpathEngine = XMLUnit.newXpathEngine()

        // build the internal document for running xpath against.
        document = XMLUnit.buildControlDocument(xml)

        // set up for operator overloading xpathHandler << "query..."
//        this.metaClass.leftShift { String query ->
//
//        }

        String.metaClass.div {XPathHandler query ->
            println "div query $delegate"
            return this.xpath(delegate)
        }
    }

    def getAt(String query) {
        println "get at $query"
        return this.xpath(query)
    }

    def leftShift(String query) {
        println "leftShift $query"
        return this.xpath(query)
    }

    /**
     * Does Spock accept a string "true" and "false" as boolean values?  No.
     * But we can fake it with a def method!  This will also allow us to use
     * String values with the same syntax
     *
     * @param query
     * @return xpath query response
     */
    def xpath(String query) {

        def val = xpathEngine.evaluate(query, document)

        if (val == "true" || val == "false") {
            return Boolean.valueOf(val)  // return a boolean so Spock is cool with it
        } else {
            return val  // return the String so that Spock can compare it
        }
    }

}

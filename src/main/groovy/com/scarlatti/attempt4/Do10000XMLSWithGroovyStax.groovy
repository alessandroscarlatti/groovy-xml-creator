package com.scarlatti.attempt4
/**
 * Created by pc on 10/18/2017.
 */
class Do10000XMLSWithGroovyStax {

    boolean shouldCreateElementX = false

    void do10000XMLSWithGroovy() {

        com.scarlatti.attempt4.XMLBuilderStax xmlBuilder = com.scarlatti.attempt4.XMLBuilderStax.defaultXMLBuilderStaxWithPrettyPrinter()

        for (int count = 0; count < 3; count++) {

            def xml = {String name, Closure c -> } << {String name -> }

            println xmlBuilder.xml {

                xml("Thing1") {
                    xml("thing2") {

                        xml("thing4")
                        xml("thing4") { "qwer" }
                        xml("thing5") {
                            for (int i = 0; i < 9; i++) {
                                if (shouldCreateElementX) {
                                    xml("SomethingSomething") {
                                        i % 3 == 0 ? "Y" : "N"
                                    }
                                } else {
                                    "Y" >> { "what do you know Y" }
                                    "Z" >> {}
                                    ZX { "asdf" }
                                    createElement("this") { "this is an element" }
                                }
                                shouldCreateElementX = !shouldCreateElementX
                            }
                        }
                    }
                }
            }.asString()  // TODO important!! asString not toString
        }
    }

    void doXml() {
        if (shouldCreateElementX) {
            PcPullChecks {
                i % 3 == 0 ? "Y" : "N"
            }
        } else {
            Y { "what do you know Y" }
            Z
            ZX { "asdf" }
            createElement("this") { "this is an element" }
        }
    }

    void do10000XMLSWithGroovy_old() {

        com.scarlatti.attempt4.XMLBuilderStax xmlBuilder = com.scarlatti.attempt4.XMLBuilderStax.defaultXMLBuilderStaxWithPrettyPrinter()

        for (int count = 0; count < 10000; count++) {
            xmlBuilder.xml {
                boolean shouldCreateElementX = false

                createElement("thing1") {
                    createElement("thing2") {
                        createElement("thing4")
                        createElement("thing4") { "qwer" }
                        createElement("thing5") {
                            for (int i = 0; i < 9; i++) {
                                if (shouldCreateElementX) {
                                    createElement("PcPullChecks") {
                                        i % 3 == 0 ? "Y" : "N"
                                    }
                                } else {
                                    createElement("Y") { getYValue() }
                                    createElement("Z")
                                    createElement("ZX") { "asdf" }
                                    createElement("this") { "this is an element" }
                                }
                                shouldCreateElementX = !shouldCreateElementX
                            }

                        }
                    }
                }
            }.toString()
        }
    }

    String getYValue() {
        return "what do you know Y"
    }

}
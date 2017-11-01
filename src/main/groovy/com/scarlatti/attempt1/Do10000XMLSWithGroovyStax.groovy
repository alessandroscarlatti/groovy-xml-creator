package com.scarlatti.attempt1

/**
 * Created by pc on 10/18/2017.
 */
class Do10000XMLSWithGroovyStax {

    void do10000XMLSWithGroovy_old() {

        XMLBuilderStax xmlBuilder = XMLBuilderStax.defaultXMLBuilderStaxWithPrettyPrinter()

        for (int count = 0; count < 10000; count++) {
            xmlBuilder.xml {
                boolean shouldCreateElementX = false

                thing1 {
                    thing2 {
                        thing4
                        thing4 { "qwer" }
                        thing5 {
                            for (int i = 0; i < 9; i++) {
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
                                shouldCreateElementX = !shouldCreateElementX
                            }

                        }
                    }
                }
            }.toString()
        }
    }

    void do10000XMLSWithGroovy() {

        XMLBuilderStax xmlBuilder = XMLBuilderStax.defaultXMLBuilderStaxWithPrettyPrinter()

        for (int count = 0; count < 10000; count++) {
            xmlBuilder.xml {
                boolean shouldCreateElementX = false

                createElement("thing1") {
                    createElement("thing2") {
                        createElement("thing4")
                        createElement("thing4") { "qwer" }
                        createElement("fillInThing") {
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

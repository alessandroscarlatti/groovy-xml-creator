package com.scarlatti.attempt3
/**
 * Created by pc on 10/18/2017.
 */
class Do10000XMLSWithGroovyStax {

    void do10000XMLSWithGroovy() {

        XMLWizard xmlBuilder = XMLWizard.defaultXMLBuilderStaxWithPrettyPrinter()

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

}

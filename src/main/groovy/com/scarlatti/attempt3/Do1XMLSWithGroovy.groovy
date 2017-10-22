package com.scarlatti.attempt3
/**
 * Created by pc on 10/18/2017.
 */
class Do1XMLSWithGroovy {

    public static void main(String[] args) {
        new Do1XMLSWithGroovy().do1XMLSWithGroovy()
    }

    void do1XMLSWithGroovy() {

        XMLWizard xmlBuilder = XMLWizard.defaultXMLBuilderStaxWithPrettyPrinter()

        for (int count = 0; count < 2; count++) {
            println xmlBuilder.xml {
                boolean shouldCreateElementX = false

                thing1 {
                    thing2 {
                        thing4
                        thing4 ""
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

    boolean shouldCreateElementX = false

    void do2XMLSWithGroovy() {

        XMLWizard xmlBuilder = XMLWizard.defaultXMLBuilderStaxWithPrettyPrinter()

        println xmlBuilder.xml {

            Thing1 {
                Thing2 {
                    Thing4
                    Thing4 { "" }
                    Thing5 {
                        for (int i = 0; i < 9; i++) {
                            if (shouldCreateElementX) {
                                ElementX { i }
                            } else {
                                ElementY { getAString() }
                            }
                            Separator { "===" }
                            shouldCreateElementX = !shouldCreateElementX
                        }
                    }
                }
            }

        }.toString()

    }

    String getAString() {
        return "a string"
    }

}

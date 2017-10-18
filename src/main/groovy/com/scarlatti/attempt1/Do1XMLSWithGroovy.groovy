package com.scarlatti.attempt1

/**
 * Created by pc on 10/18/2017.
 */
class Do1XMLSWithGroovy {

    public static void main(String[] args) {
        new Do1XMLSWithGroovy().do1XMLSWithGroovy()
    }

    void do1XMLSWithGroovy() {

        XMLBuilderStax xmlBuilder = XMLBuilderStax.defaultXMLBuilderStaxWithPrettyPrinter()

        for (int count = 0; count < 3; count++) {
            println xmlBuilder.xml {
                boolean shouldCreateElementX = false

                thing1 {
                    thing2 {
                        thing4
                        thing4 { "" }
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

package com.scarlatti.attempt1

/**
 * Created by pc on 10/18/2017.
 */
class Do10000XMLSWithGroovy {

    void do10000XMLSWithGroovy() {

        XMLBuilder xmlBuilder = new XMLBuilder()

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
            }.withPrettyPrint().toString()
        }
    }

}

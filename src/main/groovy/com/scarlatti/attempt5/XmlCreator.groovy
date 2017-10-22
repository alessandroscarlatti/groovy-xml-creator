package com.scarlatti.attempt5

/**
 * Created by pc on 10/21/2017.
 */
class XmlCreator {

    void do10000Xmls() throws Exception {
        def xml = XmlBuilder.defaultXmlBuilder()

        for (int count = 0; count < 3; count++) {
            print xml {
                boolean shouldCreateElementX = false

                xml ("thing1") {
                    xml ("thing2") {
                        xml ("thing4")
                        xml ("thing4") { "qwer" }
                        xml ("thing5") {
                            for (int i = 0; i < 9; i++) {
                                if (shouldCreateElementX) {
                                    xml ("SomethingElse") {
                                        i % 3 == 0 ? "Y" : "N"
                                    }
                                } else {
                                    xml ("Y") { "what do you know Y" }
                                    xml ("Z")
                                    xml ("ZX") { "asdf" }
                                    xml ("this") { "this is an element" }
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
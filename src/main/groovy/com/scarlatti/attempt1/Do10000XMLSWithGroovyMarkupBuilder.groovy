package com.scarlatti.attempt1

import groovy.xml.MarkupBuilder

/**
 * Created by pc on 10/18/2017.
 */
class Do10000XMLSWithGroovyMarkupBuilder {

    public static void main(String[] args) {
        new Do10000XMLSWithGroovyMarkupBuilder().doXMLs()
    }

    void doXMLs() {

        def writer = new StringWriter()
        def html = new MarkupBuilder(writer)


        for (int count = 0; count < 10000; count++) {

            html.thing1 {
                boolean shouldCreateElementX = false

                thing2 {
                    thing4 { "" }
                    thing4 { "qwer" }
                    thing5 {
                        for (int i = 0; i < 9; i++) {
                            if (shouldCreateElementX) {
                                PcPullChecks {
                                    i % 3 == 0 ? "Y" : "N"
                                }
                            } else {
                                Y { "what do you know Y" }
                                Z { "" }
                                ZX { "asdf" }
                                "thas" "this is an element"
                            }
                            shouldCreateElementX = !shouldCreateElementX
                        }

                    }
                }
            }
//            println writer
            writer.buffer.setLength(0)
            writer.buffer.trimToSize()
        }
    }

}

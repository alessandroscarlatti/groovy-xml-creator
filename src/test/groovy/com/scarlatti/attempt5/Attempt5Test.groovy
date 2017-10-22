package com.scarlatti.attempt5

import com.scarlatti.TestUtils
import org.junit.Test
import spock.lang.Specification

/**
 * Created by pc on 10/21/2017.
 */
class Attempt5Test extends Specification {

    XmlBuilder.Handler xml

    boolean shouldPrintDucks = true

    def setup() {
       xml = XmlBuilder.defaultXmlBuilder()
    }

    @Test
    "can produce xml"() {
        when:

            println xml {
                xml("Penguins") {
                    xml("Penguin") {
                        xml("Nickname", "Phillie")
                    }
                    xml("Penguin", "Phil")
                    xml("Penguin", getName())
                    xml("Woodpeckers", woodpeckers)
                }

                boolean shouldPrintFrogs = true

                if (shouldPrintFrogs) {
                    xml("Frogs") {
                        if (shouldPrintFrogs) {
                            "Y - " + TestUtils.getUtilName()
                        } else {
                            "N"
                        }
                    }
                }

                if (shouldPrintDucks) {
                    xml("Ducks") {
                        shouldPrintDucks ? "Y" : "N"
                    }
                }
            }

            println "next xml:"
            println xml {
                xml ("WhatIsThisElement") {
                    xml ("ThisIsAFunnyElement", "Haha!")
                }
            }

        then:
            notThrown(Exception)
    }

    String getName() {
        return "Phillip"
    }

    def woodpeckers = {
        xml("Woodpecker", "Charlotte")
        xml("Woodpecker", "Annie")
        xml("Woodpecker", "Quater")
        xml("Woodpecker", "Phil")
    }
}
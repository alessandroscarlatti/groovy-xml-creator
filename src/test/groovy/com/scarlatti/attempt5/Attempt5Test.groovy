package com.scarlatti.attempt5

import org.junit.Test
import spock.lang.Specification

/**
 * Created by pc on 10/21/2017.
 */
class Attempt5Test extends Specification {

    XmlBuilder.Handler xml

    boolean shouldPrintDucks = true

    def setup() {
       xml = XmlBuilder.defaultXmlBuilder().getXmlBuilder(this)
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
                    addWoodpeckers()
                }
                xml("Ducks") {
                    shouldPrintDucks ? "Y" : "N"
                }
            }

        then:
            notThrown(Exception)
    }

    String getName() {
        return "Phillip"
    }

    void addWoodpeckers() {
        xml("Woodpeckers") {
            xml("Woodpecker", "Charlotte")
        }
    }
}

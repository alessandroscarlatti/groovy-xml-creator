package com.scarlatti.attempt3

import org.junit.Test
import spock.lang.Specification

/**
 * Created by pc on 10/19/2017.
 */
class TestXMLWizard extends Specification {

    XMLWizard wizard = XMLWizard.defaultXMLBuilderStaxWithPrettyPrinter()

    String name = "Annika"

    @Test
    "xml wizard produces correct xml"() {
        when:
            String expectedXML = "<Penguins><Penguin><Name>Phillip</Name><Nickname>Phil</Nickname><Age>35</Age></Penguin>" +
                    "<Penguin><Name>Annika</Name><Nickname>Annie</Nickname><Age>25</Age></Penguin>" +
                    "</Penguins>"

        wizard.write {
            Penguins {
                buildPenguin("Phillip", "Phil", 35)
                buildPenguin(name, "Annie", 25)
            }
        }

        String actualXML = wizard.toString()

        println "actualXML: $actualXML"

        then:
            notThrown(Throwable)
            expectedXML == actualXML
    }

    static String getString() {
        return "asdf"
    }

    void buildPenguin(String name, String nickname, int age) {
        wizard.write {
            Penguin {
                Name { name }
                Nickname { nickname }
                Age { age }
            }
        }
    }

}

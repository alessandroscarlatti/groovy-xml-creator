package com.scarlatti.attempt3

import com.scarlatti.PenguinDelegate

/**
 * Created by pc on 10/19/2017.
 */
class WizardDemo {


    public static void main(String[] args) {
        WizardDemo demo = new WizardDemo()
        demo.buildPenguins()
    }

    void buildPenguins() {

        XMLWizard wizard = XMLWizard.defaultXMLBuilderStaxWithPrettyPrinter()

        wizard.write {
            println "hello penguins!"
            Penguins {
                this.buildPenguin(wizard, "Phillip", "Phil", 35)
//                buildPenguin(wizard, "Annika", "Annie", 36)
            }
        }

        println "xml is: ${wizard.toString()}"
    }

    static void buildPenguin(XMLWizard wizard, String name, String nickname, int age) {
        wizard.write {
            Penguin {
                Name { name }
                Nickname { nickname }
                Age { age }
            }
        }
    }
}

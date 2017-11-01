package com.scarlatti.attempt6

/**
 * ~     _____                                    __
 * ~    (, /  |  /)                /)         (__/  )      /)        ,
 * ~      /---| // _ _  _  _ __  _(/ __ ___     / _ _  __ // _ _/_/_
 * ~   ) /    |(/_(//_)/_)(_(/ ((_(_/ ((_)   ) / (_(_(/ ((/_(_((_(__(_
 * ~  (_/                                   (_/
 * ~  Tuesday, 10/31/2017
 */
class Experiment {

    def xml = XmlBuilder.defaultXmlBuilder()

    static void main(String[] args) {
        new Experiment().doExampleOfEncapsulation()
    }

    void doExampleOfEncapsulation() {
        xml {
            xml ("Thing") {
                fillInThing("args", "to", "pass")
            }
        }
    }

    void buildThing(String arg1, String arg2, String arg3) {
        xml ("Thing") {
            xml(arg1)
            xml(arg2)
            xml(arg3)
        }
    }

    void fillInThing(String arg1, String arg2, String arg3) {
        xml(arg1)
        xml(arg2)
        xml(arg3)
    }

    def getThing(String arg1, String arg2, String arg3) {
        return xml {
            xml(arg1)
            xml(arg2)
            xml(arg3)
        }
    }
}

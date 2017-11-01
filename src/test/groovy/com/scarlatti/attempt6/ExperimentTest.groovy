package com.scarlatti.attempt6

import org.junit.Test
import spock.lang.Specification

/**
 * ~     _____                                    __
 * ~    (, /  |  /)                /)         (__/  )      /)        ,
 * ~      /---| // _ _  _  _ __  _(/ __ ___     / _ _  __ // _ _/_/_
 * ~   ) /    |(/_(//_)/_)(_(/ ((_(_/ ((_)   ) / (_(_(/ ((/_(_((_(__(_
 * ~  (_/                                   (_/
 * ~  Tuesday, 10/31/2017
 */
class ExperimentTest extends Specification {

    @Test
    "this is how a test might look for encapsulation with a named root element"() {
        when:
            Experiment experiment = new Experiment()
            experiment.xml {
                experiment.buildThing("args", "to", "pass")
            }
            String xml = experiment.xml.getBuilder().toString()
        then:
            println xml
        and:
            xml == "<Thing><args/><to/><pass/></Thing>"
    }

    @Test
    "this is how a test might look for encapsulation without a root element"() {
        when:
            Experiment experiment = new Experiment()
            experiment.xml {
                experiment.fillInThing("args", "to", "pass")
            }
            String xml = experiment.xml.getBuilder().toString()
        then:
            println xml
        and:
            xml == "<args/><to/><pass/>"
    }

    @Test
    "this is how a test might look for the entire xml creator method"() {
        when:
            Experiment experiment = new Experiment()
            experiment.doExampleOfEncapsulation()
            String xml = experiment.xml.getBuilder().toString()
        then:
            println xml
        and:
            xml == "<Thing><args/><to/><pass/></Thing>"
    }
}

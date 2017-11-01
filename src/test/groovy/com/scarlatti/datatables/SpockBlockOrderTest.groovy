package com.scarlatti.datatables

import org.junit.Test
import spock.lang.Specification

/**
 * Created by pc on 10/24/2017.
 */
class SpockBlockOrderTest extends Specification {

    def setup() {
        println "setup method"
    }

    def setupSpec() {
        println "setup spec"
    }

    @Test
    "can unroll iteration #number"(int number) {
        setup:
            println "setup: $number"
        when:
            println ("when: $number")
        then:
            notThrown(Exception)
        where:
            number << [1, 2, 4]
    }

    @Test
    "can perform math"() {
        when:
            println "setup math"
        then:
            2 == 2
    }
}

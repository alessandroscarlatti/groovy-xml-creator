package com.scarlatti

/**
 * Created by pc on 10/14/2017.
 */
class PenguinDelegate {

    String name
    int count = 0

    PenguinDelegate(String name) {
        this.name = name
    }

    def methodMissing(String methodName, args) {
        println "called Delegate $name with method $methodName"

        if (count < 25) {
            checkIdentity()
        }

    }

    void checkIdentity() {
        println "I am $name"

        PenguinDelegate delegate = new PenguinDelegate(name + ".F")
        Closure c = { qwer() }
        c.delegate = delegate
        c()
        count++
    }

    def propertyMissing(String propertyName) {
        println "called Delegate $name for property $propertyName"
    }
}

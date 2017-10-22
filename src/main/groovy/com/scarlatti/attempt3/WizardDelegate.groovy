package com.scarlatti.attempt3

/**
 * Created by pc on 10/19/2017.
 */
class WizardDelegate {

    XMLWizard wizard

    WizardDelegate(XMLWizard wizard) {
        this.wizard = wizard
    }

    def methodMissing(String name, args) {
        println "missing method $name"
    }

    def propertyMissing(String name) {
        println "missing property $name"

        return wizard.toString()
    }
}

package com.scarlatti.validation

import org.custommonkey.xmlunit.QualifiedName
import org.custommonkey.xmlunit.XMLUnit
import org.custommonkey.xmlunit.XpathEngine
import org.custommonkey.xmlunit.exceptions.XMLUnitException
import org.custommonkey.xmlunit.jaxp13.Validator
import org.junit.Test
import spock.lang.Specification

import javax.swing.text.Document
import javax.xml.transform.stream.StreamSource

import static org.custommonkey.xmlunit.XMLAssert.assertXMLValid
import static org.custommonkey.xmlunit.XMLAssert.assertXpathEvaluatesTo
import static org.custommonkey.xmlunit.XMLAssert.assertXpathExists

/**
 * ~     _____                                    __
 * ~    (, /  |  /)                /)         (__/  )      /)        ,
 * ~      /---| // _ _  _  _ __  _(/ __ ___     / _ _  __ // _ _/_/_
 * ~   ) /    |(/_(//_)/_)(_(/ ((_(_/ ((_)   ) / (_(_(/ ((/_(_((_(__(_
 * ~  (_/                                   (_/
 * ~  Wednesday, 11/1/2017
 */
class ValidationTest extends Specification {

    @Test
    "item exists and cost is 5000"() {
        when:
            Validator validator = new Validator();
            validator.addSchemaSource(new StreamSource(this.class.getResourceAsStream("/test.xsd")));
            StreamSource is = new StreamSource(this.class.getResourceAsStream("/test.xml"));

        String xml = '''
        
        <purchase-request xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                   xsi:noNamespaceSchemaLocation="./test.xsd">
        
                <item>Widget</item>
                <cost>5000</cost>
                <signature-authority>Level1</signature-authority>
        
        </purchase-request>
        
        '''

        then:
            validator.isInstanceValid(is);
            assertXpathExists("//item", xml);
            assertXpathEvaluatesTo("5000", "//cost", xml)
    }

    @Test
    "Level 1 authority can only have cost < 10000"() {
        when:
            Validator validator = new Validator();
            validator.addSchemaSource(new StreamSource(this.class.getResourceAsStream("/test.xsd")));
            StreamSource is = new StreamSource(this.class.getResourceAsStream("/test.xml"));

            String xml = '''
        
        <purchase-request xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                   xsi:noNamespaceSchemaLocation="./test.xsd">
        
                <item>Widget</item>
                <cost>5000</cost>
                <signature-authority>Level1</signature-authority>
        
        </purchase-request>
        
        '''

            /**
             * This could be overloaded to accept a DOM document
             * TODO accept my own custom String class to be certain
             * OR just check that there is not already an existing method.
             *
             * If some other piece of code is going to overwrite my custom operator,
             * my test results would be unpredictable.
             *
             * that no operator overloading issues involving string exist?
             */
        String.metaClass.rightShift { String arg ->
            println "right shift"
            return xpath(delegate, arg)
        }

        then:
            //TODO implement xml >> validator
            validator.isInstanceValid(is)
            "boolean(//item)"  >> xml
            "5000" == "//cost" >> xml
            "//signature-authority='Level1'" >> xml
    }

    /**
     * Does Spock accept a string "true" and "false" as boolean values?  No.
     * But we can fake it with a def method!  This will also allow us to use
     * String values with the same syntax
     *
     * @param xpath
     * @return xpath query response
     */
    static def xpath(String xpath, String xml) {
        XpathEngine xpathEngine = XMLUnit.newXpathEngine()
        def val = xpathEngine.evaluate(xpath, XMLUnit.buildControlDocument(xml))

        if (val == "true" || val == "false") {
            return Boolean.valueOf(val)  // return a boolean so Spock is cool with it
        } else {
            return val  // return the String so that Spock can compare it
        }
    }
}

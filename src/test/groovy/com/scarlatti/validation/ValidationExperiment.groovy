package com.scarlatti.validation

import org.junit.Test
import spock.lang.Specification

/**
 * ~     _____                                    __
 * ~    (, /  |  /)                /)         (__/  )      /)        ,
 * ~      /---| // _ _  _  _ __  _(/ __ ___     / _ _  __ // _ _/_/_
 * ~   ) /    |(/_(//_)/_)(_(/ ((_(_/ ((_)   ) / (_(_(/ ((/_(_((_(__(_
 * ~  (_/                                   (_/
 * ~  Thursday, 11/2/2017
 */
class ValidationExperiment extends Specification {

    XmlValidator validator;

    def setup() {
        validator = new XmlValidator().withSchema("/test.xsd").withXpathAssertions { xpath ->
            assert xpath["boolean(//item)"]
            assert "5000" == xpath["//cost"]
            assert xpath["//signature-authority='Level1'"]

            assert "boolean(//item)" << xpath
            assert "5000" == "//cost" << xpath
            assert "//signature-authority='Level1'" << xpath

            assert "boolean(//item)" << xpath
            assert "5000" == "//cost" << xpath
            assert "//signature-authority='Level1'" << xpath
        }
    }

    @Test
    "can validate schema with xpath queries"() {
        when:
            String xml = '''
        
        <purchase-request xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                   xsi:noNamespaceSchemaLocation="./test.xsd">
        
                <item>Widget</item>
                <cost>5000</cost>
                <signature-authority>Level1</signature-authority>
        
        </purchase-request>
        
        '''
            // when do we create a handler?
        then:
            validator.validateXml(xml)

            assert xpath["boolean(//item)"]
            assert "5000" == xpath["//cost"]
            assert xpath["//signature-authority='Level1'"]
    }

    @Test
    "can also add test case specific xpath assertions"() {
        when:
            String xml = '''
        
        <purchase-request xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                   xsi:noNamespaceSchemaLocation="./test.xsd">
        
                <item>Widget</item>
                <cost>5000</cost>
                <signature-authority>Level1</signature-authority>
        
        </purchase-request>
        
        '''
        // TODO when do we add the xpath query handler, can we only do that
        // after the source has been added?  Or can it be dynamic behind
        // the scenes.  That might look like this:
            def xpath = validator.getXpathQueryHandler()
        then:
            validator.validateXml(xml)

            xpath["boolean(//item)"]
            "5000" == xpath["//cost"]
            xpath["//signature-authority='Level1'"]
    }
}

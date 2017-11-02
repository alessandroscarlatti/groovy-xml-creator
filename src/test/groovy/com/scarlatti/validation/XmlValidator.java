package com.scarlatti.validation;

import groovy.lang.Closure;
import org.custommonkey.xmlunit.XMLUnit;
import org.custommonkey.xmlunit.XpathEngine;
import org.custommonkey.xmlunit.jaxp13.Validator;

import javax.xml.transform.stream.StreamSource;
import java.io.StringReader;

/**
 * ~     _____                                    __
 * ~    (, /  |  /)                /)         (__/  )      /)        ,
 * ~      /---| // _ _  _  _ __  _(/ __ ___     / _ _  __ // _ _/_/_
 * ~   ) /    |(/_(//_)/_)(_(/ ((_(_/ ((_)   ) / (_(_(/ ((/_(_((_(__(_
 * ~  (_/                                   (_/
 * ~  Thursday, 11/2/2017
 */
public class XmlValidator {

    private Validator validator;
    private Closure assertions;

    /**
     * Add a schema for this schemaResourcePath from the resource path.
     * @param schemaResourcePath path to an xsd in the resources path
     * @return this schemaResourcePath, for chaining.
     */
    XmlValidator withSchema(String schemaResourcePath) {

        Validator validator = new Validator();
        validator.addSchemaSource(new StreamSource(this.getClass().getResourceAsStream(schemaResourcePath)));

        this.validator = validator;
        return this;
    }

    /**
     * Add assertions to follow schema validation.
     * @param assertions a closure of assertions
     * @return this validator instance for chaining.
     */
    XmlValidator withXpathAssertions(Closure assertions) {
        this.assertions = assertions;
        return this;
    }

    /**
     * Would have all the validation methods below...
     * Use the schema/validator built into this instance
     * by the user.
     */

    /**
     * Validation method.
     *
     * @param xml the string of xml to validate against the xsd.
     * @throws RuntimeException when xml is invalid.
     * @throws RuntimeException on error processing xml.
     */
    void validateXml(String xml) {
        // this assertion may fail.
        assert validator.isInstanceValid(new StreamSource(new StringReader(xml)));

        // now run xpath assertions

        assertions.call(new XPathHandler(xml));
    }

}

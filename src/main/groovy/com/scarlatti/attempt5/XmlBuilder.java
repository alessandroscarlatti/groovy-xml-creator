package com.scarlatti.attempt5;

import com.scarlatti.attempt1.ElementHandlerStax;
import groovy.lang.Closure;
import groovy.lang.ClosureException;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamWriter;
import java.io.StringWriter;

//import com.sun.xml.internal.txw2.output.IndentingXMLStreamWriter;
//import com.sun.xml.internal.txw2.output.IndentingXMLStreamWriter;


/**
 * @author Alessandro Scarlatti
 */
public class XmlBuilder {

    private StringWriter writer;
    private XMLStreamWriter xmlStreamWriter;

    private XmlBuilder() throws Exception {
        writer = new StringWriter();
    }

    public static XmlBuilder defaultXmlBuilder() throws Exception {
        XmlBuilder xmlBuilder = new XmlBuilder();

        XMLOutputFactory xmlOutputFactory = XMLOutputFactory.newInstance();
        xmlBuilder.xmlStreamWriter = xmlOutputFactory.createXMLStreamWriter(xmlBuilder.writer);
        // TODO close this stream, make this class implement closeable (spring context would close it)

        return xmlBuilder;
    }

    public XmlBuilder root(String name, Closure xml) {  // TODO could have empty root
        System.out.println("printing the root");
        return this;
    }

    @Override
    public String toString() {

        try {
            xmlStreamWriter.flush();
            return writer.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "Exception caught! " + e;
        }

    }

    // a Handler instance should be associated with an XMLBuilder instance.
    static class Handler extends Closure {

        XmlBuilder builder;

        Handler(Object owner, XmlBuilder builder) {
            super(owner);
            this.builder = builder;
        }

        public XmlBuilder doCall() throws Exception {
            return builder;
        }

        public XmlBuilder doCall(Closure xml) throws Exception {
            System.out.println("doCall for xml: " + xml);
            xml.call();
            return builder;
        }

        public XmlBuilder doCall(String name) throws Exception {
            System.out.println("doCall for name: " + name);
            builder.addEmptyNodeWithOneTag(name);
            return builder;
        }

        public XmlBuilder doCall(String name, String text) throws Exception {
            System.out.println("doCall for name: " + name + ", text: " + text);
            builder.addElementWithText(name, text);
            return builder;
        }

        public XmlBuilder doCall(String name, Closure xml) throws Exception {
            System.out.println("doCall for name: " + name + ", xml: " + xml);
            builder.addNewElement(name, xml);
            return builder;
        }


    }

    public Closure getXmlBuilder(Object owner) {
        return new Handler(owner, this);
    }

//    public Closure xml(Closure traverseXML) throws Exception {
//
//        xmlStreamWriter.flush();
//        writer.getBuffer().setLength(0);
//        writer.getBuffer().trimToSize();
//
//        handleElement(traverseXML);
//
//        return this;
//    }

    void addEmptyNodeWithOneTag(String name) throws Exception {
        xmlStreamWriter.writeEmptyElement(name);
    }

    void addElementWithText(String name, String text) throws Exception {
        xmlStreamWriter.writeStartElement(name);
        xmlStreamWriter.writeCharacters(text);
        xmlStreamWriter.writeEndElement();
    }

    private void addTextValue(String text) throws Exception {
        xmlStreamWriter.writeCharacters(text);
    }

    void addNewElement(String name, Closure xml) throws Exception {

        xmlStreamWriter.writeStartElement(name);

        Object val = handleElement(xml);
        if (val != null)
            if (val instanceof String)
                addTextValue(String.valueOf(val));

        xmlStreamWriter.writeEndElement();
    }

    private Object handleElement(Closure xml) throws Exception {
        return xml.call();
    }
}

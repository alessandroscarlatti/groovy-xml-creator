package com.scarlatti.attempt5;

import groovy.lang.Closure;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamWriter;
import java.io.StringWriter;

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

    public static Handler defaultXmlBuilder() throws Exception {
        XmlBuilder xmlBuilder = new XmlBuilder();

        XMLOutputFactory xmlOutputFactory = XMLOutputFactory.newInstance();
//        xmlBuilder.xmlStreamWriter = new IndentingXMLStreamWriter(xmlOutputFactory.createXMLStreamWriter(xmlBuilder.writer));
        xmlBuilder.xmlStreamWriter = xmlOutputFactory.createXMLStreamWriter(xmlBuilder.writer);
        // TODO close this stream, make this class implement closeable (spring context would close it)

        return new Handler(null, xmlBuilder);
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
    static class Handler extends Closure<XmlBuilder> {

        XmlBuilder builder;

        Handler(Object owner, XmlBuilder builder) {
            super(owner);
            this.builder = builder;
        }

        public XmlBuilder doCall() throws Exception {
            return builder;
        }

        public XmlBuilder doCall(Closure xml) throws Exception {
//            System.out.println("doCall for xml: " + xml);

            // reset the writer
            builder.xmlStreamWriter.flush();
            builder.writer.getBuffer().setLength(0);
            builder.writer.getBuffer().trimToSize();

            // run the xml
            xml.call();
            return builder;
        }

        public XmlBuilder doCall(String name) throws Exception {
//            System.out.println("doCall for name: " + name);
            builder.addEmptyNodeWithOneTag(name);
            return builder;
        }

        public XmlBuilder doCall(String name, String text) throws Exception {
//            System.out.println("doCall for name: " + name + ", text: " + text);
            builder.addElementWithText(name, text);
            return builder;
        }

        public XmlBuilder doCall(String name, Closure xml) throws Exception {
//            System.out.println("doCall for name: " + name + ", xml: " + xml);
            builder.addNewElement(name, xml);
            return builder;
        }
    }

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
            if (!(val instanceof XmlBuilder))
                addTextValue(String.valueOf(val));

        xmlStreamWriter.writeEndElement();
    }

    private Object handleElement(Closure xml) throws Exception {
        return xml.call();
    }
}

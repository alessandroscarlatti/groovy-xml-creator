package com.scarlatti.attempt6;

import groovy.lang.Closure;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamWriter;
import java.io.StringWriter;


/**
 * Abstract class for an XmlBuilder.
 * I plan to implement a StreamingXmlBuilder
 * and a DomXmlBuilder.
 *
 * The interface for both should be similar.
 * However, each will have its own handler.
 *
 * A StreamingXmlBuilder will be understood to hold
 * state during streaming mode, and will have methods
 * such as peekXmlString, buildXmlString (ending that stream
 * and disposing of resources for that stream).
 *
 * Unit testing a StreamingXmlBuilder xml node written as part
 * of a method would require setup of the stream state.
 *
 * For encapsulated construction, if the xml() root method has not been
 * called, it should be called to create a streaming context.  However,
 * on second thought, that would slow down the streaming to have to make
 * that decision each time.
 *
 * Would the api for outsourced, encapsulated xml elements have to be
 * different for DOM, or could I pick up a reference to the specific
 * element being appended?  Keep in mind that when using DOM, users
 * most likely expect to be able to perform operations somewhat out of
 * order and still achieve the same results.
 *
 * @author Alessandro Scarlatti
 */
public class XmlBuilder {

    /**
     * String writer reused by a builder instance.
     */
    private StringWriter writer;

    /**
     * XMLStreamWriter reused by a builder instance.
     */
    private XMLStreamWriter xmlStreamWriter;

    /**
     * Initialize the builder with a StringWriter instance.
     * @throws Exception if StringWriter fails.
     */
    private XmlBuilder() throws Exception {
        writer = new StringWriter();
    }

    /**
     * This is a default xml Handler.
     *
     * @return a default xml handler associated to a
     * fresh instance of an XmlBuilder.
     * @throws Exception if streams fail.
     */
    public static Handler defaultXmlBuilder() throws Exception {
        XmlBuilder xmlBuilder = new XmlBuilder();

        XMLOutputFactory xmlOutputFactory = XMLOutputFactory.newInstance();
//        xmlBuilder.xmlStreamWriter = new IndentingXMLStreamWriter(xmlOutputFactory.createXMLStreamWriter(xmlBuilder.writer));
        xmlBuilder.xmlStreamWriter = xmlOutputFactory.createXMLStreamWriter(xmlBuilder.writer);
        // TODO close this stream, make this class implement closeable (spring context would close it)

        return new Handler(null, xmlBuilder);
    }

    /**
     * Using toString() right now to print finished XML.
     * This could change later so that toString() does not
     * destroy the state of the stream!
     *
     * Furthermore, the xmlStreamWriter seems to need something
     * written to the stream to fully flush the content.
     *
     * @return the finished XML string.
     */
    @Override
    public String toString() {

        try {
            xmlStreamWriter.writeCharacters("");  // try to induce the stream writer to flush!
            xmlStreamWriter.flush();
            return writer.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "Exception caught! " + e;
        }

    }


    /**
     * This is the "closure" that the user will interact with.
     * Because it extends Closure, this class has extended
     * functionality, the "builder" property, for example.
     */
    // a Handler instance should be associated with an XMLBuilder instance.
    static class Handler extends Closure<XmlBuilder> {

        /**
         * The builder associated to this handler (reused)
         * across xml constructions.
         */
        private XmlBuilder builder;

        Handler(Object owner, XmlBuilder builder) {
            super(owner);
            this.builder = builder;
        }

        public XmlBuilder builder() {
            return builder;
        }

        public XmlBuilder doCall() throws Exception {
            return builder;
        }

        /**
         * Xml construction method for root element.
         * @param xml the closure containing all the xml.
         * @return whatever the builder?  Or should I return the handler?
         * @throws Exception if streams fail.
         */
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

        /**
         * Xml construction method for empty single tag elements.
         * @param name name of the tag
         * @return the builder?
         * @throws Exception if streams fail.
         */
        public XmlBuilder doCall(String name) throws Exception {
//            System.out.println("doCall for name: " + name);
            builder.addEmptyNodeWithOneTag(name);
            return builder;
        }

        /**
         * Xml construction method for simple set of tags with text content.
         * @param name name of the tag.
         * @param text the text content for the tag.
         * @return the builder?
         * @throws Exception if streams fail.
         */
        public XmlBuilder doCall(String name, String text) throws Exception {
//            System.out.println("doCall for name: " + name + ", text: " + text);
            builder.addElementWithText(name, text);
            return builder;
        }

        /**
         * Xml construction method for a tag with nested XML.
         * @param name name of the tag
         * @param xml nested xml (in the form of another closure
         *            containing more xml construction method calls))
         * @return the builder?
         * @throws Exception if streams fail?
         */
        public XmlBuilder doCall(String name, Closure xml) throws Exception {
//            System.out.println("doCall for name: " + name + ", xml: " + xml);
            builder.addComplexElement(name, xml);
            return builder;
        }

        public XmlBuilder getBuilder() {
            return builder;
        }

        public void setBuilder(XmlBuilder builder) {
            this.builder = builder;
        }
    }

    /**
     * Helper methods for building elements in xmlStreamWriter.
     */

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

    void addComplexElement(String name, Closure xml) throws Exception {

        xmlStreamWriter.writeStartElement(name);

        Object val = handleElement(xml);
        if (val != null)
            if (!(val instanceof XmlBuilder))
                addTextValue(String.valueOf(val));

        xmlStreamWriter.writeEndElement();
    }

    /**
     * Semantically clear up usage of the closure.
     * @param xml the xml closure to execute
     * @return the result returned by executing the closure.
     * @throws Exception if the closure fails.
     */
    private static Object handleElement(Closure xml) throws Exception {
        return xml.call();
    }
}

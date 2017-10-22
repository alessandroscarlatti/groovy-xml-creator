package com.scarlatti.attempt1;

//import com.sun.xml.internal.txw2.output.IndentingXMLStreamWriter;
//import com.sun.xml.internal.txw2.output.IndentingXMLStreamWriter;
import groovy.lang.Closure;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamWriter;
import java.io.StringWriter;

/**
 * @author Alessandro Scarlatti
 */
public class XMLBuilderStax {

    private ElementHandlerStax handler;
    private StringWriter writer;
    private XMLStreamWriter xmlStreamWriter;

    private XMLBuilderStax() throws Exception {
        writer = new StringWriter();
        handler = new ElementHandlerStax(this);
    }

    public static XMLBuilderStax defaultXMLBuilderStax() throws Exception {
        XMLBuilderStax xmlBuilderStax = new XMLBuilderStax();

        XMLOutputFactory xmlOutputFactory = XMLOutputFactory.newInstance();
        xmlBuilderStax.xmlStreamWriter = xmlOutputFactory.createXMLStreamWriter(xmlBuilderStax.writer);

        return xmlBuilderStax;
    }

    public static XMLBuilderStax defaultXMLBuilderStaxWithPrettyPrinter() throws Exception {
        XMLBuilderStax xmlBuilderStax = new XMLBuilderStax();

        XMLOutputFactory xmlOutputFactory = XMLOutputFactory.newInstance();
//        xmlBuilderStax.xmlStreamWriter = new IndentingXMLStreamWriter(xmlOutputFactory.createXMLStreamWriter(xmlBuilderStax.writer));
        // TODO close this stream, make this class implement closeable (spring context would close it)

        return xmlBuilderStax;
    }

    @Override
    public String toString() {

        try {
            xmlStreamWriter.flush();
            return writer.toString();
        } catch (Exception e){
            e.printStackTrace();
            return "Exception caught! " + e;
        }

    }

    static XMLBuilderStax createXML(Closure traverseXML) throws Exception {
        XMLBuilderStax builder = new XMLBuilderStax();
        builder.xml(traverseXML);

        return builder;
    }

    public XMLBuilderStax xml(Closure traverseXML) throws Exception {

        xmlStreamWriter.flush();
        writer.getBuffer().setLength(0);
        writer.getBuffer().trimToSize();

        handleElement(traverseXML);

        return this;
    }

    private void handleElement(Closure traverseElement) throws Exception {
        traverseElement.setDelegate(handler);
        traverseElement.setResolveStrategy(Closure.OWNER_FIRST);  // OWNER_FIRST is about three times slower!!

        Object val = traverseElement.call();

        if (val != null) {
            addTextValue(String.valueOf(val));
        }
    }

    void handleTag(String name, Object[] args) throws Exception {
        if (args == null)
            addEmptyNodeWithOneTag(name);

        else if (args[0] instanceof String)
            addElementWithText(name, (String) args[0]);

        else if (args[0] instanceof Closure)

            addNewElement(name, (Closure) args[0]);
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

    void addNewElement(String name, Closure traverseElement) throws Exception {

        xmlStreamWriter.writeStartElement(name);
        handleElement(traverseElement);  // sort of recursive call
        xmlStreamWriter.writeEndElement();
    }
}

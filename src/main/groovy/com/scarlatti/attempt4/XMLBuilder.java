package com.scarlatti.attempt4;

import groovy.lang.Closure;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;

/**
 * Created by pc on 10/14/2017.
 */
public class XMLBuilder {

    private Element root;
    private ElementHandler handler;
    private Element thisElement;

    private DOMSource domSource;
    private StringWriter writer;
    private StreamResult result;
    private Transformer transformer;

    private boolean withPrettyPrint = false;

     public XMLBuilder() throws Exception {
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        Document doc = docBuilder.newDocument();
        root = doc.createElement("root");
        doc.appendChild(root);

        TransformerFactory tf = TransformerFactory.newInstance();
        transformer = tf.newTransformer();
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");

        domSource = new DOMSource();
        writer = new StringWriter();
        result = new StreamResult(writer);

        handler = new ElementHandler(this);
    }

    @Override
    public String toString() {

        try {
            writer.getBuffer().setLength(0);
            writer.getBuffer().trimToSize();

            domSource.setNode(root.getFirstChild());
            transformer.transform(domSource, result);

            domSource.setNode(null); // is this necessary?

            return writer.toString();
        } catch (Exception e){
            e.printStackTrace();
            return "Exception " + e;
        }

    }

    static XMLBuilder createXML(Closure<String> traverseXML) throws Exception {
        XMLBuilder builder = new XMLBuilder();
        builder.xml(traverseXML);

        return builder;
    }

    public XMLBuilder xml(Closure<String> traverseXML) {

        while (root.hasChildNodes()) {
            root.removeChild(root.getFirstChild());  // in case there are multiple nodes?
        }

        handleElement(root, traverseXML);

        return this;
    }

    private Element handleElement(Element element, Closure<String> traverseElement) {

        Element captureThisElement = thisElement;

        thisElement = element;

        traverseElement.setDelegate(handler);
        traverseElement.setResolveStrategy(Closure.DELEGATE_FIRST);

// I just removed the null check for traverseElement
        String text = traverseElement.call();

        if (text != null)
            addTextValue(text);


        thisElement = captureThisElement;

        return thisElement;
    }

    @SuppressWarnings("unchecked")
    void handleTag(String name, Object[] args) {
        if (args.length == 0)
            addEmptyNodeWithOneTag(name);

        else if (args[0] instanceof String)
            addElementWithText(name, (String) args[0]);

        else if (args[0] instanceof Closure)

            addNewElement(name, (Closure<String>) args[0]);
    }

    void addEmptyNodeWithOneTag(String name) {
        Element element = thisElement.getOwnerDocument().createElement(name);
        thisElement.appendChild(element);
    }

    // TODO can I set an empty pair of tags?
    void addElementWithText(String name, String text) {
        Element element = thisElement.getOwnerDocument().createElement(name);
        element.setTextContent(text);
        thisElement.appendChild(element);
    }

    private void addTextValue(String text) {
        thisElement.setTextContent(text);
    }

    void addNewElement(String name, Closure<String> traverseElement) {
        // recursive call
        Element element = thisElement.getOwnerDocument().createElement(name);
        thisElement.appendChild(element);

        handleElement(element, traverseElement);
    }

    public XMLBuilder withPrettyPrint() {

        if (!withPrettyPrint) {
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
        }

        return this;
    }
}

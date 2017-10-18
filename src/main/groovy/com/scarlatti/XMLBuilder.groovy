package com.scarlatti

import org.w3c.dom.Document
import org.w3c.dom.Element

import javax.xml.parsers.DocumentBuilder
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.transform.OutputKeys
import javax.xml.transform.Transformer
import javax.xml.transform.TransformerFactory
import javax.xml.transform.dom.DOMSource
import javax.xml.transform.stream.StreamResult

/**
 * Created by pc on 10/14/2017.
 */
class XMLBuilder {

    Element root
    ElementHandler handler
    DOMSource domSource
    StringWriter writer
    StreamResult result
    Transformer transformer

    XMLBuilder() {
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance()
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder()
        Document doc = docBuilder.newDocument()
        root = doc.createElement("root")
        doc.appendChild(root)

        TransformerFactory tf = TransformerFactory.newInstance()
        transformer = tf.newTransformer()
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes")

        domSource = new DOMSource()
        writer = new StringWriter()
        result = new StreamResult(writer)
    }

    @Override
    String toString() {

        writer.buffer.setLength(0)
        writer.buffer.trimToSize()

        domSource.node = root.firstChild
        transformer.transform(domSource, result)

        return writer.toString()
    }

    static XMLBuilder createXML(Closure traverseXML) {
        XMLBuilder builder = new XMLBuilder()
        builder.xml(traverseXML)

        return builder
    }

    XMLBuilder xml(Closure traverseXML) {
        while (root.hasChildNodes()) {
            root.removeChild(root.firstChild)
        }

        if (handler == null) {
            ElementHandler handler = new ElementHandler(root, traverseXML)
            handler.handleElement()
        }

        return this
    }

    XMLBuilder withPrettyPrint() {
        transformer.setOutputProperty(OutputKeys.INDENT, "yes")
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2")

        return this
    }
}

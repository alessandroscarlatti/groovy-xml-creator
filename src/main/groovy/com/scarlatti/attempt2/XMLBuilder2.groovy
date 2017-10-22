package com.scarlatti.attempt2

import com.scarlatti.attempt1.ElementHandler
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
class XMLBuilder2 {

    Element root
    ElementHandler handler
    DOMSource domSource
    StringWriter writer
    StreamResult result
    Transformer transformer

    boolean withPrettyPrint = false

    XMLBuilder2() {
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

//        // TODO teach the metaclass about this property?
//        ElementHandler.metaClass.propertyMissing = {String name ->
//            delegate.handleTag(name)
//        }
//
//        // teach the metaclass about this method
//        ElementHandler.metaClass.methodMissing = { String name, args ->
////            ElementHandler.metaClass."$name" = { tagArgs ->
////                delegate.handleTag(name, tagArgs)
////            }
//
//            delegate.handleTag(name, args)
//        }
    }

    @Override
    String toString() {

        writer.buffer.setLength(0)
        writer.buffer.trimToSize()
//        writer.buffer.setLength(2000)  // THIS IS INEFFICIENT!!!

        domSource.node = root.firstChild
        transformer.transform(domSource, result)

        domSource.node = null // is this necessary?

        return writer.toString()
    }

    static XMLBuilder2 createXML(Closure traverseXML) {
        XMLBuilder2 builder = new XMLBuilder2()
        builder.xml(traverseXML)

        return builder
    }

    XMLBuilder2 xml(Closure traverseXML) {
        if (root.hasChildNodes()) {
            root.removeChild(root.firstChild)  // in case there are multiple nodes?
        }

        if (handler == null) {
            ElementHandler handler = new ElementHandler(root, traverseXML)  // can I reuse anything here?
            handler.handleElement()
        }

        return this
    }

    XMLBuilder2 withPrettyPrint() {

        if (!withPrettyPrint) {
            transformer.setOutputProperty(OutputKeys.INDENT, "yes")
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2")
        }

        return this
    }
}

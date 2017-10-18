package com.scarlatti;

import org.w3c.dom.Attr;
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
import java.time.Duration;

/**
 * Created by pc on 10/17/2017.
 */
public class JavaPerformanceTest {

    public static void main(String[] args) throws Exception {

        Long sum = 0L;

        for (int count = 0; count < 50; count++) {

            Long startTime = System.nanoTime();

            do10000XMLSWithJava();

            Long endTime = System.nanoTime();
            Long durationCount = endTime - startTime;
            Duration duration = Duration.ofNanos(durationCount);

            System.out.println("Java xml creation took " + duration.toString());

            sum += duration.toNanos();
        }

        sum = sum / 50;
        Duration avg = Duration.ofNanos(sum);
        System.out.println("Java xml creation avg took " + avg.toString());
    }

    static void do10000XMLSWithJava() throws Exception {
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        Document doc = docBuilder.newDocument();

        // write the content into createXML file
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();

        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

        // just so it can be removed the first time...
        Element root = doc.createElement("root");
        doc.appendChild(root);

        DOMSource domSource = new DOMSource(root);
        StringWriter writer = new StringWriter();
        StreamResult result = new StreamResult(writer);

        for (int count = 0; count < 10000; count++) {

            doc.removeChild(doc.getFirstChild());

            // root elements
            Element thing1 = doc.createElement("thing1");
            doc.appendChild(thing1);

            Element thing2 = doc.createElement("thing2");
            thing1.appendChild(thing2);

            Element thing5 = doc.createElement("thing5");
            thing2.appendChild(thing5);

            Element thing4_1 = doc.createElement("thing4");
            thing5.appendChild(thing4_1);

            Element thing4_2 = doc.createElement("thing4");
            thing5.appendChild(thing4_2);

            boolean shouldCreateElementX = false;

            for (int i = 0; i < 9; i++) {
                if (shouldCreateElementX) {

                    Element pullChecksElement = doc.createElement("PcPullChecks");
                    thing5.appendChild(pullChecksElement);

                    pullChecksElement.setTextContent(i % 3 == 0 ? "Y" : "N");

                } else {

                    Element elementY = doc.createElement("Y");
                    thing5.appendChild(elementY);
                    elementY.setTextContent("what do you know Y");

                    Element elementZ = doc.createElement("Z");
                    thing5.appendChild(elementZ);

                    Element elementZX = doc.createElement("ZX");
                    thing5.appendChild(elementZX);
                    elementZX.setTextContent("asdf");

                    Element elementThis = doc.createElement("this");
                    thing5.appendChild(elementThis);
                    elementThis.setTextContent("this is an element");

                }
                shouldCreateElementX = !shouldCreateElementX;
            }

            domSource.setNode(thing1);
            writer.getBuffer().setLength(0);
            writer.getBuffer().trimToSize();

            transformer.transform(domSource, result);

//            System.out.println(writer.toString());
        }
    }
}

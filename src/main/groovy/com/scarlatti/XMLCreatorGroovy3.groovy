package com.scarlatti

import com.scarlatti.attempt1.XMLBuilder
import org.w3c.dom.Attr
import org.w3c.dom.Document
import org.w3c.dom.Element

import javax.xml.parsers.DocumentBuilder
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.transform.Transformer
import javax.xml.transform.TransformerFactory

/**
 * Created by pc on 10/14/2017.
 */
class XMLCreatorGroovy3 {

    static void main(String[] args) {

        XMLBuilder xmlBuilder = new XMLBuilder()

        (1..100).each {
            println xmlBuilder.xml {
                boolean shouldCreateElementX = false

                thing1 {
                    thing2 {
                        thing4  // TODO what happens if I define a metaclass property thing4, but then later use a metaclass method thing4?  Does that work?
                        thing4
                        thing5 {
                            for (int i = 0; i < 9; i++) {
                                if (shouldCreateElementX) {
                                    PcPullChecks {
                                        i % 3 == 0 ? "Y" : "N"
                                    }
                                } else {
                                    Y { "what do you know Y" }
                                    Z
                                    ZX { "asdf" }
                                    createElement("this") { "this is an element" }
                                }
                                shouldCreateElementX = !shouldCreateElementX
                            }

                        }
                    }
                }
            }.withPrettyPrint().toString()
        }

    }

    static void createXML() {
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

        // root elements
        Document doc = docBuilder.newDocument();
        Element rootElement = doc.createElement("company");
        doc.appendChild(rootElement);

        // staff elements
        Element staff = doc.createElement("Staff");
        rootElement.appendChild(staff);

        // set attribute to staff element
        Attr attr = doc.createAttribute("id");
        attr.setValue("1");
        staff.setAttributeNode(attr);

        // shorten way
        // staff.setAttribute("id", "1");

        // firstname elements
        Element firstname = doc.createElement("firstname");
        firstname.appendChild(doc.createTextNode("yong"));
        staff.appendChild(firstname);

        // lastname elements
        Element lastname = doc.createElement("lastname");
        lastname.appendChild(doc.createTextNode("mook kim"));
        staff.appendChild(lastname);

        // nickname elements
        Element nickname = doc.createElement("nickname");
        nickname.appendChild(doc.createTextNode("mkyong"));
        staff.appendChild(nickname);

        // salary elements
        Element salary = doc.createElement("salary");
        salary.appendChild(doc.createTextNode("100000"));
        staff.appendChild(salary);

        // write the content into createXML file
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
    }



}

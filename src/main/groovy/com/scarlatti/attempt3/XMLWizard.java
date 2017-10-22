package com.scarlatti.attempt3;

//import com.sun.xml.internal.txw2.output.IndentingXMLStreamWriter;
import groovy.lang.Closure;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Alessandro Scarlatti
 */
public class XMLWizard {

    private WizardElementHandlerStax handler;
    private StringWriter writer;
    private XMLStreamWriter xmlStreamWriter;

    Map<String, String> insideMethodsMap = new HashMap<>();
    Map<String, String> insidePropsMap = new HashMap<>();

    private String isInsideMethod;
    private String isInsideProp;

    private boolean firstTime = true;

    private XMLWizard() throws Exception {
        writer = new StringWriter();
        handler = new WizardElementHandlerStax(this);
    }

    public static XMLWizard defaultXMLBuilderStax() throws Exception {
        XMLWizard xmlWizard = new XMLWizard();

        XMLOutputFactory xmlOutputFactory = XMLOutputFactory.newInstance();
        xmlWizard.xmlStreamWriter = xmlOutputFactory.createXMLStreamWriter(xmlWizard.writer);

        return xmlWizard;
    }

    public static XMLWizard defaultXMLBuilderStaxWithPrettyPrinter() throws Exception {
        XMLWizard xmlWizard = new XMLWizard();

        XMLOutputFactory xmlOutputFactory = XMLOutputFactory.newInstance();
//        xmlWizard.xmlStreamWriter =  new IndentingXMLStreamWriter(xmlOutputFactory.createXMLStreamWriter(xmlWizard.writer));
        // TODO close this stream, make this class implement closeable (spring context would close it)

        return xmlWizard;
    }

    public Object handleMissingMethod(String name, Object[] args, Object owner) throws Exception {
        isInsideMethod = insideMethodsMap.get(name);

        if (isInsideMethod != null) {

            return handleKnownMethod(name, args);

        } else {
            return handleUnknownMethod(name, args);
        }
    }

    public Object handleKnownMethod(String name, Object[] args) throws Exception {
        isInsideMethod = insideMethodsMap.get(name);

        if (isInsideMethod.equals("i")) {
            handleTag(name, args);
            return null;
        } else {
            return handler.invokeMethodOnOwner(name, args);
        }
    }

    public Object handleUnknownMethod(String name, Object[] args) throws Exception {
        try {
            Object val = handler.invokeMethodOnOwner(name, args);

            // if it was successful, return the value and cache this method
            insideMethodsMap.put(name, "o");
            return val;
        } catch (Exception e) {
            insideMethodsMap.put(name, "i");
            handleTag(name, args);
            return null;
        }
    }


//    public Closure<Void> getWizard() throws Exception {
//
//        XMLOutputFactory xmlOutputFactory = XMLOutputFactory.newInstance();
//        xmlStreamWriter = new IndentingXMLStreamWriter(xmlOutputFactory.createXMLStreamWriter(writer));
//        // TODO close this stream, make this class implement closeable (spring context would close it)
//
//
//        XMLWizard wizard = this;
//
//        // return a closure that can be called with a parameter
//        Closure<Void> closure = new Closure<Void>(new WizardDelegate(this), this) {
//            @Override
//            public Void call(Object... args) {
//                try {
//                    Closure traverseXML = (Closure) args[0];
//                    this.rehydrate(new WizardDelegate(wizard), traverseXML.getOwner(), null);
//                    xml((Closure)args[0]);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//                return null;
//            }
//        };
//
//        return closure;
//    }

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

    static XMLWizard createXML(Closure traverseXML) throws Exception {
        XMLWizard builder = new XMLWizard();
        builder.xml(traverseXML);

        return builder;
    }

    public XMLWizard xml(Closure traverseXML) throws Exception {

        xmlStreamWriter.flush();
        writer.getBuffer().setLength(0);
        writer.getBuffer().trimToSize();

        traverseXML.setResolveStrategy(Closure.OWNER_FIRST);

        handler.setOwner(traverseXML.getOwner());
        handleElement(traverseXML);

        if (firstTime) {
            handler.setSecondTime();
            firstTime = false;
        }

        return this;
    }

    public XMLWizard write(Closure traverseXML) throws Exception {
        handler.setOwner(traverseXML.getOwner());
        handleElement(traverseXML);
        return this;
    }

    private void handleElement(Closure traverseElement) throws Exception {
        traverseElement.setDelegate(handler);
        traverseElement.setResolveStrategy(Closure.DELEGATE_FIRST);

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

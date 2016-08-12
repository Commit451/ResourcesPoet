package com.commit451.resourcespoet;

import org.w3c.dom.Attr;
import org.w3c.dom.Comment;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

/**
 * Helps generate XML configuration files for Android
 */
public class ResourcesPoet {

    private static final String ELEMENT_RESOURCES = "resources";

    private static DocumentBuilderFactory sDocumentBuilderFactory;
    private static DocumentBuilder sDocumentBuilder;

    /**
     * Create a new builder
     * @return a resources builder
     */
    public static ResourcesPoet create() {
        init();
        Document document = sDocumentBuilder.newDocument();
        Element resources = document.createElement(ELEMENT_RESOURCES);
        document.appendChild(resources);
        return create(document, resources);
    }

    /**
     * Creates a builder on top of the current resources XML file
     * @param file the resources file you want to add to
     * @return the builder
     * @throws IOException
     * @throws SAXException
     */
    public static ResourcesPoet create(File file) throws IOException, SAXException {
        init();
        Document document = sDocumentBuilder.parse(file);
        Element resources;
        NodeList list = document.getElementsByTagName(ELEMENT_RESOURCES);
        if (list == null || list.getLength() == 0) {
            resources = document.createElement(ELEMENT_RESOURCES);
            document.appendChild(resources);
        } else {
            resources = (Element) list.item(0);
        }
        return create(document, resources);
    }

    private static ResourcesPoet create(Document document, Element resourceElement) {
        ResourcesPoet poet = new ResourcesPoet();
        poet.document = document;
        poet.resourceElement = resourceElement;
        return poet;
    }

    private static void init() {
        if (sDocumentBuilderFactory == null || sDocumentBuilder == null) {
            try {
                sDocumentBuilderFactory = DocumentBuilderFactory.newInstance();
                sDocumentBuilder = sDocumentBuilderFactory.newDocumentBuilder();
            } catch (Exception nope) {
                //Welp
                System.exit(1);
            }
        }
    }

    private Document document;
    private Element resourceElement;

    private ResourcesPoet() {
        //use the builder
    }

    public void addBool(String name, boolean value) {
        Attr attr = document.createAttribute("name");
        attr.setValue(name);

        Element bool = document.createElement("bool");
        bool.setAttributeNode(attr);
        bool.appendChild(value ? document.createTextNode("true") : document.createTextNode("false"));
        resourceElement.appendChild(bool);
    }

    public void addString(String name, String value) {
        Attr attr = document.createAttribute("name");
        attr.setValue(name);

        Element bool = document.createElement("string");
        bool.setAttributeNode(attr);
        bool.appendChild(document.createTextNode(value));
        resourceElement.appendChild(bool);
    }

    public void addColor(String name, String hexStringColorValue) {
        Attr attr = document.createAttribute("name");
        attr.setValue(name);

        Element bool = document.createElement("color");
        bool.setAttributeNode(attr);
        bool.appendChild(document.createTextNode(hexStringColorValue));
        resourceElement.appendChild(bool);
    }

    public void addComment(String comment) {
        Comment commentNode = document.createComment(comment);
        resourceElement.appendChild(commentNode);
    }

    public void build(StreamResult result) throws TransformerException {
        build(result, false);
    }

    public void build(StreamResult result, boolean pretty) throws TransformerException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.ENCODING, "utf-8");
        if (pretty) {
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        }
        DOMSource source = new DOMSource(document);
        transformer.transform(source, result);
    }
}

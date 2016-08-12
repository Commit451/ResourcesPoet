package com.commit451.resourcespoet;

import org.jetbrains.annotations.Nullable;
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

    private static DocumentBuilderFactory sDocumentBuilderFactory;
    private static DocumentBuilder sDocumentBuilder;

    /**
     * Create a new builder
     * @return a resources builder
     */
    public static ResourcesPoet create() {
        try {
            return createInternal(null);
        } catch (Exception e) {
            //This won't happen
            return null;
        }
    }

    /**
     * Creates a builder on top of the current resources XML file
     * @param file the resources file you want to add to
     * @return the builder
     * @throws IOException
     * @throws SAXException
     */
    public static ResourcesPoet create(File file) throws IOException, SAXException {
        return createInternal(file);
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

    private static ResourcesPoet createInternal(@Nullable File file) throws IOException, SAXException {
        init();
        ResourcesPoet xmlResourcesBuilder = new ResourcesPoet();

        Document document;
        if (file == null) {
            document = sDocumentBuilder.newDocument();
        } else {
            document = sDocumentBuilder.parse(file);
        }
        xmlResourcesBuilder.document = document;

        Element resources;
        //If this is a valid config, there will only be one
        NodeList list = document.getElementsByTagName("resources");
        if (list == null || list.getLength() == 0) {
            resources = document.createElement("resources");
            document.appendChild(resources);
        } else {
            resources = (Element) list.item(0);
        }
        xmlResourcesBuilder.resourceElement = resources;
        return xmlResourcesBuilder;
    }

    private ResourcesPoet() {
        //use the builder
    }

    private Document document;
    private Element resourceElement;

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

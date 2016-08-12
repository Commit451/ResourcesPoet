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

    /**
     * Add a boolean to the config
     * @param name the name
     * @param value the value
     * @return poet
     */
    public ResourcesPoet addBool(String name, boolean value) {
        //<bool name="is_production">false</bool>
        Element element = document.createElement("bool");
        element.setAttribute("name", name);
        element.appendChild(value ? document.createTextNode("true") : document.createTextNode("false"));
        resourceElement.appendChild(element);
        return this;
    }

    /**
     * Add a string to the config
     * @param name the name
     * @param value the value
     * @return poet
     */
    public ResourcesPoet addString(String name, String value) {
        //<string name="app_name">Cool</string>
        Element element = document.createElement("string");
        element.setAttribute("name", name);
        element.appendChild(document.createTextNode(value));
        resourceElement.appendChild(element);
        return this;
    }

    /**
     * Add a color to the config
     * @param name the name
     * @param hexStringColorValue the value
     * @return poet
     */
    public ResourcesPoet addColor(String name, String hexStringColorValue) {
        //<color name="color_primary">#7770CB</color>
        Element element = document.createElement("color");
        element.setAttribute("name", name);
        element.appendChild(document.createTextNode(hexStringColorValue));
        resourceElement.appendChild(element);
        return this;
    }

    /**
     * Add a drawable to the config
     * @param name the name
     * @param ref the value
     * @return poet
     */
    public ResourcesPoet addDrawable(String name, String ref) {
        //<drawable name="logo">@drawable/logo</drawable>
        Element bool = document.createElement("drawable");
        bool.setAttribute("name", name);
        bool.appendChild(document.createTextNode(ref));
        resourceElement.appendChild(bool);
        return this;
    }

    /**
     * Add a style to the config
     * @param name the name
     * @param parentRef a ref to the style parent
     * @return poet
     */
    public ResourcesPoet addStyle(String name, @Nullable String parentRef) {
        //<style name="AppTheme.Dark" parent="Base.AppTheme.Dark"/>
        Element element = document.createElement("style");
        element.setAttribute("name", name);
        if (parentRef != null) {
            element.setAttribute("parent", parentRef);
        }
        resourceElement.appendChild(element);
        return this;
    }

    /**
     * Add a comment to the config
     * @param comment the comment to add
     * @return poet
     */
    public ResourcesPoet addComment(String comment) {
        Comment commentNode = document.createComment(comment);
        resourceElement.appendChild(commentNode);
        return this;
    }

    /**
     * Build the XML to the {@link StreamResult}
     * @param result the result
     * @throws TransformerException
     */
    public void build(StreamResult result) throws TransformerException {
        build(result, false);
    }

    /**
     * Build the XML to the {@link StreamResult} and indents to make it more readable
     * @param result the result
     * @param indent true if you want indentation
     * @throws TransformerException
     */
    public void build(StreamResult result, boolean indent) throws TransformerException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.ENCODING, "utf-8");
        if (indent) {
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        }
        DOMSource source = new DOMSource(document);
        transformer.transform(source, result);
    }
}

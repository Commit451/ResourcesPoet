package com.commit451.resourcespoet;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Helps generate XML configuration files for Android
 */
public class ResourcesPoet {

    private static final String ELEMENT_RESOURCES = "resources";

    private static DocumentBuilderFactory sDocumentBuilderFactory;
    private static DocumentBuilder sDocumentBuilder;
    private static TransformerFactory transformerFactory;

    /**
     * Create a new builder
     *
     * @return poet
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
     *
     * @param file the resources file you want to add to
     * @return poet
     */
    public static ResourcesPoet create(File file) {
        try {
            return create(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            throw new IllegalStateException("Unable to parse the resource file you passed. Make sure it is properly formatted", e);
        }
    }

    /**
     * Creates a builder on top of the current resources XML file
     *
     * @param inputStream the input stream of the resources file you want to add to
     * @return poet
     */
    public static ResourcesPoet create(InputStream inputStream) {
        init();
        try {
            Document document = sDocumentBuilder.parse(inputStream);
            Element resources;
            NodeList list = document.getElementsByTagName(ELEMENT_RESOURCES);
            if (list == null || list.getLength() == 0) {
                resources = document.createElement(ELEMENT_RESOURCES);
                document.appendChild(resources);
            } else {
                resources = (Element) list.item(0);
            }
            return create(document, resources);
        } catch (IOException | SAXException e) {
            throw new IllegalStateException("Unable to parse the resource file you passed. Make sure it is properly formatted", e);
        }
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
            } catch (ParserConfigurationException exception) {
                //Welp
                throw new IllegalStateException("Unable to create a ResourcePoet");
            }
            transformerFactory = TransformerFactory.newInstance();
        }
    }

    private Document document;
    private Element resourceElement;
    private boolean indent;

    private ResourcesPoet() {
        //use the builder
    }

    /**
     * Add an attr to the config
     *
     * @param attr the defined attribute
     * @return poet
     */
    public ResourcesPoet addAttr(Attr attr) {
        //<attr name="gravityX" format="float"/>
        Element element = document.createElement(Type.ATTR.toString());
        element.setAttribute("name", attr.name);
        if (attr.formats != null && !attr.formats.isEmpty()) {
            String formatString = "";
            for (Attr.Format format : attr.formats) {
                formatString = formatString + format.toString() + "|";
            }
            //remove last |
            formatString = formatString.substring(0, formatString.length() - 1);
            element.setAttribute("format", formatString);
        }
        resourceElement.appendChild(element);
        return this;
    }

    /**
     * Add a boolean to the config
     *
     * @param name  the name
     * @param value the value
     * @return poet
     */
    public ResourcesPoet addBool(String name, boolean value) {
        addBool(name, String.valueOf(value));
        return this;
    }

    /**
     * Add a boolean to the config
     *
     * @param name  the name
     * @param value the value
     * @return poet
     */
    public ResourcesPoet addBool(String name, String value) {
        //<bool name="is_production">false</bool>
        Element element = document.createElement(Type.BOOL.toString());
        element.setAttribute("name", name);
        element.appendChild(document.createTextNode(value));
        resourceElement.appendChild(element);
        return this;
    }

    /**
     * Add a color to the config
     *
     * @param name  the name
     * @param value the value
     * @return poet
     */
    public ResourcesPoet addColor(String name, String value) {
        //<color name="color_primary">#7770CB</color>
        Element element = document.createElement(Type.COLOR.toString());
        element.setAttribute("name", name);
        element.appendChild(document.createTextNode(value));
        resourceElement.appendChild(element);
        return this;
    }

    /**
     * Add a comment to the config
     *
     * @param comment the comment to add
     * @return poet
     */
    public ResourcesPoet addComment(String comment) {
        Comment commentNode = document.createComment(comment);
        resourceElement.appendChild(commentNode);
        return this;
    }

    /**
     * Add a drawable to the config
     *
     * @param name  the name
     * @param value the value
     * @return poet
     */
    public ResourcesPoet addDrawable(String name, String value) {
        //<drawable name="logo">@drawable/logo</drawable>
        Element bool = document.createElement(Type.DRAWABLE.toString());
        bool.setAttribute("name", name);
        bool.appendChild(document.createTextNode(value));
        resourceElement.appendChild(bool);
        return this;
    }

    /**
     * Add a dimension to the config
     *
     * @param name  the name
     * @param value the value
     * @return poet
     */
    public ResourcesPoet addDimension(String name, String value) {
        //<drawable name="logo">@drawable/logo</drawable>
        Element bool = document.createElement(Type.DIMENSION.toString());
        bool.setAttribute("name", name);
        bool.appendChild(document.createTextNode(value));
        resourceElement.appendChild(bool);
        return this;
    }

    /**
     * Add an id to the config
     *
     * @param id the id
     * @return poet
     */
    public ResourcesPoet addId(String id) {
        //        <item
        //                type="id"
        //        name="id_name" />
        Element bool = document.createElement(Type.ID.toString());
        bool.setAttribute("name", id);
        bool.setAttribute("type", "id");
        resourceElement.appendChild(bool);
        return this;
    }

    /**
     * Add an integer to the config
     *
     * @param name  the name
     * @param value the value
     * @return poet
     */
    public ResourcesPoet addInteger(String name, Integer value) {
        addInteger(name, String.valueOf(value));
        return this;
    }

    /**
     * Add an integer to the config
     *
     * @param name  the name
     * @param value the value
     * @return poet
     */
    public ResourcesPoet addInteger(String name, String value) {
        //<drawable name="logo">@drawable/logo</drawable>
        Element bool = document.createElement(Type.INTEGER.toString());
        bool.setAttribute("name", name);
        bool.appendChild(document.createTextNode(String.valueOf(value)));
        resourceElement.appendChild(bool);
        return this;
    }

    /**
     * Add an integer array to the config
     *
     * @param name   the name
     * @param values the value
     * @return poet
     */
    public ResourcesPoet addIntegerArray(String name, @NotNull List<Integer> values) {
        ArrayList<String> integers = new ArrayList<>();
        for (Integer value : values) {
            integers.add(String.valueOf(value));
        }
        addIntegerArrayStrings(name, integers);
        return this;
    }

    /**
     * Add an integer array to the config
     *
     * @param name   the name
     * @param values the value
     * @return poet
     */
    public ResourcesPoet addIntegerArrayStrings(String name, @NotNull List<String> values) {
        // <integer-array name="numbers">
        //      <item>0</item>
        //      <item>1</item>
        // </integer-array>
        Element element = document.createElement(Type.INTEGER_ARRAY.toString());
        element.setAttribute("name", name);
        for (String value : values) {
            //Does this mess up the ordering?
            Element valueElement = document.createElement("item");
            valueElement.appendChild(document.createTextNode(value));
            element.appendChild(valueElement);
        }
        resourceElement.appendChild(element);
        return this;
    }

    /**
     * Add a plural strings array to the config
     *
     * @param name    the name
     * @param plurals the plurals
     * @return poet
     */
    public ResourcesPoet addPlurals(String name, @NotNull List<Plural> plurals) {
        //    <plurals name="numberOfSongsAvailable">
        //        <item quantity="one">Znaleziono %d piosenkę.</item>
        //        <item quantity="few">Znaleziono %d piosenki.</item>
        //        <item quantity="other">Znaleziono %d piosenek.</item>
        //    </plurals>
        Element element = document.createElement(Type.PLURALS.toString());
        element.setAttribute("name", name);
        for (Plural plural : plurals) {
            //Does this mess up the ordering?
            Element valueElement = document.createElement("item");
            valueElement.setAttribute("quantity", plural.quantity.toString());
            valueElement.appendChild(document.createTextNode(plural.value));
            element.appendChild(valueElement);
        }
        resourceElement.appendChild(element);

        return this;
    }

    /**
     * Add a string to the config
     *
     * @param name  the name
     * @param value the value
     * @return poet
     */
    public ResourcesPoet addString(String name, String value) {
        //<string name="app_name">Cool</string>
        Element element = document.createElement(Type.STRING.toString());
        element.setAttribute("name", name);
        element.appendChild(document.createTextNode(value));
        resourceElement.appendChild(element);
        return this;
    }

    /**
     * Add a String array to the config
     *
     * @param name   the name
     * @param values the value
     * @return poet
     */
    public ResourcesPoet addStringArray(String name, @NotNull List<String> values) {
        //<string-array name="country_names">
        //      <item>Country</item>
        //      <item>United States</item>
        // </string-array>
        Element element = document.createElement(Type.STRING_ARRAY.toString());
        element.setAttribute("name", name);
        for (String value : values) {
            //Does this mess up the ordering?
            Element valueElement = document.createElement("item");
            valueElement.appendChild(document.createTextNode(value));
            element.appendChild(valueElement);
        }
        resourceElement.appendChild(element);
        return this;
    }

    /**
     * Add a style to the config
     *
     * @param name      the name
     * @param parentRef a ref to the style parent
     * @return poet
     */
    public ResourcesPoet addStyle(String name, @Nullable String parentRef) {
        return addStyle(name, parentRef, null);
    }

    /**
     * Add a style to the config
     *
     * @param name      the name
     * @param parentRef a ref to the style parent
     * @return poet
     */
    public ResourcesPoet addStyle(String name, @Nullable String parentRef, @Nullable List<StyleItem> styleItems) {
        //<style name="AppTheme.Dark" parent="Base.AppTheme.Dark"/>
        Element element = document.createElement(Type.STYLE.toString());
        element.setAttribute("name", name);
        if (parentRef != null) {
            element.setAttribute("parent", parentRef);
        }
        if (styleItems != null) {
            for (StyleItem item : styleItems) {
                Element valueElement = document.createElement("item");
                valueElement.setAttribute("name", item.name);
                valueElement.appendChild(document.createTextNode(item.value));
                element.appendChild(valueElement);
            }
        }
        resourceElement.appendChild(element);
        return this;
    }

    /**
     * Add a typed array to the config
     *
     * @param name   the name
     * @param values the value
     * @return poet
     */
    public ResourcesPoet addTypedArray(String name, @NotNull List<String> values) {
        //<array name="country_names">
        //      <item>Country</item>
        //      <item>United States</item>
        // </array>
        Element element = document.createElement(Type.TYPED_ARRAY.toString());
        element.setAttribute("name", name);
        for (String value : values) {
            Element valueElement = document.createElement("item");
            valueElement.appendChild(document.createTextNode(value));
            element.appendChild(valueElement);
        }
        resourceElement.appendChild(element);
        return this;
    }

    /**
     * Remove the resource which matches the name and type
     * @param type the type of the resource you wish to remove
     * @param name the name of the element to remove
     * @return poet
     */
    public ResourcesPoet remove(@NotNull Type type, @NotNull String name) {
        NodeList nodeList = resourceElement.getElementsByTagName(type.toString());
        for (int i=0; i<nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node instanceof  Element && name.equals(((Element)node).getAttribute("name"))) {
                //For some reason, this will remove the element and leave a line break in its place
                //Somewhat unfortunate but I do not think there is much we could do about it
                resourceElement.removeChild(nodeList.item(i));
            }
        }
        return this;
    }

    /**
     * Get the value of the current resource of this type and name
     * @param type the type
     * @param name the name
     * @return the value or null if it does not exist
     */
    @Nullable
    public String value(@NotNull Type type, @NotNull String name) {
        NodeList nodeList = resourceElement.getElementsByTagName(type.toString());
        for (int i=0; i<nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node instanceof  Element && name.equals(((Element)node).getAttribute("name"))) {
                //For some reason, this will remove the element and leave a line break in its place
                //Somewhat unfortunate but I do not think there is much we could do about it
                return nodeList.item(i).getTextContent();
            }
        }
        return null;
    }

    /**
     * Specify if you want the output to be indented or not
     *
     * @param indent true if you want indentation. false if not. Default is false
     * @return poet
     */
    public ResourcesPoet indent(boolean indent) {
        this.indent = indent;
        return this;
    }

    /**
     * Build the XML to a string
     *
     * @return the xml as a string
     */
    public String build() {
        StringWriter writer = new StringWriter();
        StreamResult result = new StreamResult(writer);
        build(result);
        return writer.toString();
    }

    /**
     * Build the XML to a file. You should call {@link File#createNewFile()} or validate that the file exists
     * before calling
     *
     * @param file the file to output the XML to
     */
    public void build(File file) {
        StreamResult result = new StreamResult(file);
        build(result);
    }

    /**
     * Build the XML to the {@link OutputStream}
     *
     * @param outputStream the output stream to output the XML to
     */
    public void build(OutputStream outputStream) {
        StreamResult result = new StreamResult(outputStream);
        build(result);
    }

    /**
     * Build the XML to the {@link Writer}
     *
     * @param writer the writer to output the XML to
     */
    public void build(Writer writer) {
        StreamResult result = new StreamResult(writer);
        build(result);
    }

    /**
     * Build the XML to the {@link StreamResult}
     *
     * @param result the result
     */
    private void build(StreamResult result) {
        try {
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.ENCODING, "utf-8");
            if (indent) {
                transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            }
            DOMSource source = new DOMSource(document);
            transformer.transform(source, result);
        } catch (TransformerConfigurationException e) {
            throw new RuntimeException("Something is seriously wrong with the ResourcePoet configuration");
        } catch (TransformerException e) {
            throw new RuntimeException("Transformer exception when trying to build result");
        }
    }
}

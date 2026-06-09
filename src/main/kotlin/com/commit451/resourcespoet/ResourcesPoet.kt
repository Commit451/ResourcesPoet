package com.commit451.resourcespoet

import org.w3c.dom.Document
import org.w3c.dom.Element
import org.xml.sax.SAXException
import java.io.*
import javax.xml.parsers.DocumentBuilder
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.parsers.ParserConfigurationException
import javax.xml.transform.OutputKeys
import javax.xml.transform.TransformerFactory
import javax.xml.transform.dom.DOMSource
import javax.xml.transform.stream.StreamResult

/**
 * Helps generate XML resource files for Android
 */
class ResourcesPoet private constructor(
    private val document: Document,
    private val resourceElement: Element,
    private var indent: Boolean = INDENT_DEFAULT
) {

    companion object {

        enum class ELEMENT(val elementName: String) {
            RESOURCES("resources"),
            FONT_FAMILIES("font-family")
        }

        private const val INDENT_DEFAULT = false
        private const val TOOLS_NAMESPACE = "http://schemas.android.com/tools"

        private val transformerFactory: TransformerFactory by lazy { TransformerFactory.newInstance() }

        private val documentBuilder: DocumentBuilder by lazy {
            try {
                DocumentBuilderFactory.newInstance().newDocumentBuilder()
            } catch (exception: ParserConfigurationException) {
                throw IllegalStateException("Unable to create a ResourcePoet")
            }
        }

        /**
         * Create a new builder
         *
         * @return poet
         */
        fun create(indent: Boolean = INDENT_DEFAULT, elementType: ELEMENT = ELEMENT.RESOURCES): ResourcesPoet {
            val document = documentBuilder.newDocument()
            val element = document.createElement(elementType.elementName)
            if (elementType == ELEMENT.FONT_FAMILIES) {
                element.setAttributeNS(
                    "http://www.w3.org/2000/xmlns/",
                    "xmlns:android",
                    "http://schemas.android.com/apk/res/android"
                )
            }
            document.appendChild(element)
            return ResourcesPoet(document, element, indent)
        }

        /**
         * Creates a builder on top of the current resources XML file
         *
         * @param file the resources file you want to add to
         * @param indent whether to use indentation
         * @param elementType the type of resource element
         * @return poet
         */
        fun create(file: File, indent: Boolean = INDENT_DEFAULT, elementType: ELEMENT = ELEMENT.RESOURCES): ResourcesPoet {
            try {
                return create(FileInputStream(file), indent, elementType)
            } catch (e: FileNotFoundException) {
                throw IllegalStateException(
                    "Unable to parse the resource file you passed. Make sure it is properly formatted",
                    e
                )
            }
        }

        /**
         * Creates a builder on top of the current resources XML file
         *
         * @param inputStream the input stream of the resources file you want to add to
         * @return poet
         */
        fun create(
            inputStream: InputStream,
            indent: Boolean = INDENT_DEFAULT,
            elementType: ELEMENT = ELEMENT.RESOURCES
        ): ResourcesPoet {
            try {
                val document = documentBuilder.parse(inputStream)
                val element: Element
                val list = document.getElementsByTagName(elementType.elementName)
                if (list == null || list.length == 0) {
                    element = document.createElement(elementType.elementName)
                    document.appendChild(element)
                } else {
                    element = list.item(0) as Element
                }
                return ResourcesPoet(document, element, indent)
            } catch (e: IOException) {
                throw IllegalStateException(
                    "Unable to parse the resource file you passed. Make sure it is properly formatted",
                    e
                )
            } catch (e: SAXException) {
                throw IllegalStateException(
                    "Unable to parse the resource file you passed. Make sure it is properly formatted",
                    e
                )
            }
        }
    }

    private var hasToolsNamespace = false

    private fun ensureToolsNamespace() {
        if (!hasToolsNamespace) {
            resourceElement.setAttributeNS(
                "http://www.w3.org/2000/xmlns/",
                "xmlns:tools",
                TOOLS_NAMESPACE
            )
            hasToolsNamespace = true
        }
    }

    private fun setToolsIgnore(element: Element, toolsIgnore: String?) {
        if (toolsIgnore != null) {
            ensureToolsNamespace()
            element.setAttribute("tools:ignore", toolsIgnore)
        }
    }

    /**
     * Set tools:ignore at the top-level <resources> element to suppress lint warnings
     * for all resources in the file.
     *
     * @param toolsIgnore lint rule names to suppress (e.g., "UnusedResource,TypographyDashes")
     * @return poet
     */
    fun toolsIgnore(toolsIgnore: String): ResourcesPoet {
        ensureToolsNamespace()
        resourceElement.setAttribute("tools:ignore", toolsIgnore)
        return this
    }

    /**
     * Add an attr to the XML file
     *
     * @param attr the defined attribute
     * @param toolsIgnore lint rule names to suppress (e.g., "UnusedResource")
     * @return poet
     */
    fun addAttr(attr: Attr, toolsIgnore: String? = null): ResourcesPoet {
        //<attr name="gravityX" format="float"/>
        val element = document.createElement(Type.ATTR.toString())
        element.setAttribute("name", attr.name)
        if (!attr.formats.isEmpty()) {
            element.setAttribute("format", attr.formats.joinToString("|") { it.toString() })
        }
        setToolsIgnore(element, toolsIgnore)
        resourceElement.appendChild(element)
        return this
    }

    /**
     * Add a boolean to the XML file
     *
     * @param name  the name
     * @param value the value
     * @param toolsIgnore lint rule names to suppress (e.g., "UnusedResource")
     * @return poet
     */
    fun addBool(name: String, value: Boolean, toolsIgnore: String? = null): ResourcesPoet {
        addBool(name, value.toString(), toolsIgnore)
        return this
    }

    /**
     * Add a boolean to the XML file
     *
     * @param name  the name
     * @param value the value
     * @param toolsIgnore lint rule names to suppress (e.g., "UnusedResource")
     * @return poet
     */
    fun addBool(name: String, value: String, toolsIgnore: String? = null): ResourcesPoet {
        //<bool name="is_production">false</bool>
        val element = document.createElement(Type.BOOL.toString())
        element.setAttribute("name", name)
        setToolsIgnore(element, toolsIgnore)
        element.appendChild(document.createTextNode(value))
        resourceElement.appendChild(element)
        return this
    }

    /**
     * Add a color to the XML file
     *
     * @param name  the name
     * @param value the value
     * @param toolsIgnore lint rule names to suppress (e.g., "UnusedResource")
     * @return poet
     */
    fun addColor(name: String, value: String, toolsIgnore: String? = null): ResourcesPoet {
        //<color name="color_primary">#7770CB</color>
        val element = document.createElement(Type.COLOR.toString())
        element.setAttribute("name", name)
        setToolsIgnore(element, toolsIgnore)
        element.appendChild(document.createTextNode(value))
        resourceElement.appendChild(element)
        return this
    }

    /**
     * Add a comment to the XML file
     *
     * @param comment the comment to add
     * @return poet
     */
    fun addComment(comment: String): ResourcesPoet {
        val commentNode = document.createComment(comment)
        resourceElement.appendChild(commentNode)
        return this
    }

    /**
     * Add a drawable to the XML file
     *
     * @param name  the name
     * @param value the value
     * @param toolsIgnore lint rule names to suppress (e.g., "UnusedResource")
     * @return poet
     */
    fun addDrawable(name: String, value: String, toolsIgnore: String? = null): ResourcesPoet {
        //<drawable name="logo">@drawable/logo</drawable>
        val bool = document.createElement(Type.DRAWABLE.toString())
        bool.setAttribute("name", name)
        setToolsIgnore(bool, toolsIgnore)
        bool.appendChild(document.createTextNode(value))
        resourceElement.appendChild(bool)
        return this
    }

    /**
     * Add a dimension to the XML file
     *
     * @param name  the name
     * @param value the value
     * @param toolsIgnore lint rule names to suppress (e.g., "UnusedResource")
     * @return poet
     */
    fun addDimension(name: String, value: String, toolsIgnore: String? = null): ResourcesPoet {
        //<dimen name="logo">@dimen/logo</dimen>
        val bool = document.createElement(Type.DIMENSION.toString())
        bool.setAttribute("name", name)
        setToolsIgnore(bool, toolsIgnore)
        bool.appendChild(document.createTextNode(value))
        resourceElement.appendChild(bool)
        return this
    }

    /**
     * Add an id to the XML file
     *
     * @param id the id
     * @param toolsIgnore lint rule names to suppress (e.g., "UnusedResource")
     * @return poet
     */
    fun addId(id: String, toolsIgnore: String? = null): ResourcesPoet {
        //        <item
        //                type="id"
        //        name="id_name" />
        val bool = document.createElement(Type.ID.toString())
        bool.setAttribute("name", id)
        bool.setAttribute("type", "id")
        setToolsIgnore(bool, toolsIgnore)
        resourceElement.appendChild(bool)
        return this
    }

    /**
     * Add an integer to the XML file
     *
     * @param name  the name
     * @param value the value
     * @param toolsIgnore lint rule names to suppress (e.g., "UnusedResource")
     * @return poet
     */
    fun addInteger(name: String, value: Int?, toolsIgnore: String? = null): ResourcesPoet {
        addInteger(name, value.toString(), toolsIgnore)
        return this
    }

    /**
     * Add an integer to the XML file
     *
     * @param name  the name
     * @param value the value
     * @param toolsIgnore lint rule names to suppress (e.g., "UnusedResource")
     * @return poet
     */
    fun addInteger(name: String, value: String, toolsIgnore: String? = null): ResourcesPoet {
        //<integer name="logo">@integer/logo</integer>
        val bool = document.createElement(Type.INTEGER.toString())
        bool.setAttribute("name", name)
        setToolsIgnore(bool, toolsIgnore)
        bool.appendChild(document.createTextNode(value))
        resourceElement.appendChild(bool)
        return this
    }

    /**
     * Add an integer array to the XML file
     *
     * @param name   the name
     * @param values the value
     * @param toolsIgnore lint rule names to suppress (e.g., "UnusedResource")
     * @return poet
     */
    fun addIntegerArray(name: String, values: List<Int>, toolsIgnore: String? = null): ResourcesPoet {
        val integers = ArrayList<String>()
        for (value in values) {
            integers.add(value.toString())
        }
        addIntegerArrayStrings(name, integers, toolsIgnore)
        return this
    }

    /**
     * Add an integer array to the XML file
     *
     * @param name   the name
     * @param values the value
     * @param toolsIgnore lint rule names to suppress (e.g., "UnusedResource")
     * @return poet
     */
    fun addIntegerArrayStrings(name: String, values: List<String>, toolsIgnore: String? = null): ResourcesPoet {
        // <integer-array name="numbers">
        //      <item>0</item>
        //      <item>1</item>
        // </integer-array>
        val element = document.createElement(Type.INTEGER_ARRAY.toString())
        element.setAttribute("name", name)
        setToolsIgnore(element, toolsIgnore)
        for (value in values) {
            val valueElement = document.createElement("item")
            valueElement.appendChild(document.createTextNode(value))
            element.appendChild(valueElement)
        }
        resourceElement.appendChild(element)
        return this
    }

    /**
     * Add a plural strings array to the XML file
     *
     * @param name    the name
     * @param plurals the plurals
     * @param toolsIgnore lint rule names to suppress (e.g., "UnusedResource")
     * @return poet
     */
    fun addPlurals(name: String, plurals: List<Plural>, toolsIgnore: String? = null): ResourcesPoet {
        //    <plurals name="numberOfSongsAvailable">
        //        <item quantity="one">Znaleziono %d piosenkę.</item>
        //        <item quantity="few">Znaleziono %d piosenki.</item>
        //        <item quantity="other">Znaleziono %d piosenek.</item>
        //    </plurals>
        val element = document.createElement(Type.PLURALS.toString())
        element.setAttribute("name", name)
        setToolsIgnore(element, toolsIgnore)
        for (plural in plurals) {
            val valueElement = document.createElement("item")
            valueElement.setAttribute("quantity", plural.quantity.toString())
            valueElement.appendChild(document.createTextNode(plural.value))
            element.appendChild(valueElement)
        }
        resourceElement.appendChild(element)

        return this
    }

    /**
     * Add a string to the XML file
     *
     * @param name  the name
     * @param value the value
     * @param translatable whether this string should be translated
     * @param toolsIgnore lint rule names to suppress (e.g., "UnusedResource")
     * @return poet
     */
    fun addString(name: String, value: String, translatable: Boolean = true, toolsIgnore: String? = null): ResourcesPoet {
        //<string name="app_name" translatable="false">Cool</string>
        val element = document.createElement(Type.STRING.toString())
        element.setAttribute("name", name)
        if (!translatable) {
            element.setAttribute("translatable", "false")
        }
        setToolsIgnore(element, toolsIgnore)
        element.appendChild(document.createTextNode(value))
        resourceElement.appendChild(element)
        return this
    }

    /**
     * Add a String array to the XML file
     *
     * @param name   the name
     * @param values the value
     * @param translatable whether this array should be translatable
     * @param toolsIgnore lint rule names to suppress (e.g., "UnusedResource")
     * @return poet
     */
    fun addStringArray(name: String, values: List<String>, translatable: Boolean = true, toolsIgnore: String? = null): ResourcesPoet {
        //<string-array name="country_names">
        //      <item>Country</item>
        //      <item>United States</item>
        // </string-array>
        val element = document.createElement(Type.STRING_ARRAY.toString())
        element.setAttribute("name", name)
        if (!translatable) {
            element.setAttribute("translatable", "false")
        }
        setToolsIgnore(element, toolsIgnore)
        for (value in values) {
            val valueElement = document.createElement("item")
            valueElement.appendChild(document.createTextNode(value))
            element.appendChild(valueElement)
        }
        resourceElement.appendChild(element)
        return this
    }

    /**
     * Add a style to the XML
     *
     * @param name      the name
     * @param parentRef a ref to the style parent
     * @param styleItems list of style items
     * @param format the format attribute for the style
     * @param toolsIgnore lint rule names to suppress (e.g., "UnusedResource")
     * @return poet
     */
    fun addStyle(name: String, parentRef: String? = null, styleItems: List<StyleItem>? = null, format: String? = null, toolsIgnore: String? = null): ResourcesPoet {
        //<style name="AppTheme.Dark" parent="Base.AppTheme.Dark"/>
        val element = document.createElement(Type.STYLE.toString())
        element.setAttribute("name", name)
        if (parentRef != null) {
            element.setAttribute("parent", parentRef)
        }
        if (format != null) {
            element.setAttribute("format", format)
        }
        setToolsIgnore(element, toolsIgnore)
        if (styleItems != null) {
            for (item in styleItems) {
                val valueElement = document.createElement("item")
                valueElement.setAttribute("name", item.name)
                valueElement.appendChild(document.createTextNode(item.value))
                element.appendChild(valueElement)
            }
        }
        resourceElement.appendChild(element)
        return this
    }

    /**
     * Add a typed array to the XML
     *
     * @param name   the name
     * @param values the value
     * @param translatable whether this array should be translatable
     * @param toolsIgnore lint rule names to suppress (e.g., "UnusedResource")
     * @return poet
     */
    fun addTypedArray(name: String, values: List<String>, translatable: Boolean = true, toolsIgnore: String? = null): ResourcesPoet {
        //<array name="country_names">
        //      <item>Country</item>
        //      <item>United States</item>
        // </array>
        val element = document.createElement(Type.TYPED_ARRAY.toString())
        element.setAttribute("name", name)
        if (!translatable) {
            element.setAttribute("translatable", "false")
        }
        setToolsIgnore(element, toolsIgnore)
        for (value in values) {
            val valueElement = document.createElement("item")
            valueElement.appendChild(document.createTextNode(value))
            element.appendChild(valueElement)
        }
        resourceElement.appendChild(element)
        return this
    }

    /**
     * Add a font family attr
     *
     * @param fontFamily the defined Font Family
     * @param toolsIgnore lint rule names to suppress (e.g., "UnusedResource")
     * @return poet
     */
    fun addFontFamily(fontFamily: FontFamily, toolsIgnore: String? = null): ResourcesPoet {
        val element = document.createElement(Type.FONT.toString())
        element.setAttribute("android:fontStyle", fontFamily.fontStyle)
        element.setAttribute("android:fontWeight", fontFamily.fontWeight)
        element.setAttribute("android:font", fontFamily.font)
        setToolsIgnore(element, toolsIgnore)
        resourceElement.appendChild(element)
        return this
    }

    /**
     * Add a fraction to the XML file
     *
     * @param name  the name
     * @param value the value (e.g. "50%p")
     * @param toolsIgnore lint rule names to suppress (e.g., "UnusedResource")
     * @return poet
     */
    fun addFraction(name: String, value: String, toolsIgnore: String? = null): ResourcesPoet {
        //<fraction name="fraction_value">50%p</fraction>
        val element = document.createElement(Type.FRACTION.toString())
        element.setAttribute("name", name)
        setToolsIgnore(element, toolsIgnore)
        element.appendChild(document.createTextNode(value))
        resourceElement.appendChild(element)
        return this
    }

    /**
     * Add a reference to the XML file
     *
     * @param name    the name
     * @param reference the reference to add
     * @param toolsIgnore lint rule names to suppress (e.g., "UnusedResource")
     * @return poet
     */
    fun addReference(name: String, reference: Reference, toolsIgnore: String? = null): ResourcesPoet {
        // <item type="reference" name="name">@type/name</item>
        val element = document.createElement("item")
        element.setAttribute("type", "reference")
        element.setAttribute("name", name)
        element.appendChild(document.createTextNode(reference.toValue()))
        setToolsIgnore(element, toolsIgnore)
        resourceElement.appendChild(element)
        return this
    }

    /**
     * Add a reference to the XML file
     *
     * @param name    the name
     * @param type    the reference type (e.g. "drawable", "color")
     * @param refName the reference name
     * @param toolsIgnore lint rule names to suppress (e.g., "UnusedResource")
     * @return poet
     */
    fun addReference(name: String, type: String, refName: String, toolsIgnore: String? = null): ResourcesPoet {
        addReference(name, Reference(type, refName), toolsIgnore)
        return this
    }

    /**
     * Add type to the XML file by its type. Currently supported types:
     *
     * [Type.BOOL]
     *
     * [Type.COLOR]
     *
     * [Type.DIMENSION]
     *
     * [Type.DRAWABLE]
     *
     * [Type.INTEGER]
     *
     * [Type.STRING]
     *
     * [Type.FRACTION]
     *
     * Any other type will throw an [IllegalArgumentException], as they have a special configuration
     *
     * @param type the type of the resource you wish to add
     * @param name the name of the element
     * @param value the value of the element
     * @param toolsIgnore lint rule names to suppress (e.g., "UnusedResource")
     * @return poet
     */
    fun add(type: Type, name: String, value: String, toolsIgnore: String? = null): ResourcesPoet {
        return when (type) {
            Type.BOOL -> addBool(name, value, toolsIgnore)
            Type.COLOR -> addColor(name, value, toolsIgnore)
            Type.DIMENSION -> addDimension(name, value, toolsIgnore)
            Type.DRAWABLE -> addDrawable(name, value, toolsIgnore)
            Type.INTEGER -> addInteger(name, value, toolsIgnore)
            Type.STRING -> addString(name, value, toolsIgnore = toolsIgnore)
            Type.FRACTION -> addFraction(name, value, toolsIgnore)
            else -> throw IllegalArgumentException("Cannot add type $type. It has a special configuration")
        }
    }

    /**
     * Remove the resource which matches the name and type
     *
     * @param type the type of the resource you wish to remove
     * @param name the name of the element to remove
     * @return poet
     */
    fun remove(type: Type, name: String): ResourcesPoet {
        val nodeList = resourceElement.getElementsByTagName(type.toString())
        for (i in 0 until nodeList.length) {
            val node = nodeList.item(i)
            if (node is Element && name == node.getAttribute("name")) {
                //For some reason, this will remove the element and leave a line break in its place
                //Somewhat unfortunate but I do not think there is much we could do about it
                resourceElement.removeChild(nodeList.item(i))
            }
        }
        return this
    }

    /**
     * Get the value of the current resource of this type and name
     *
     * @param type the type
     * @param name the name
     * @return the value or null if it does not exist
     */
    fun value(type: Type, name: String): String? {
        val nodeList = resourceElement.getElementsByTagName(type.toString())
        for (i in 0 until nodeList.length) {
            val node = nodeList.item(i)
            if (node is Element && name == node.getAttribute("name")) {
                //For some reason, this will remove the element and leave a line break in its place
                //Somewhat unfortunate but I do not think there is much we could do about it
                return nodeList.item(i).textContent
            }
        }
        return null
    }

    /**
     * Specify if you want the output to be indented or not
     *
     * @param indent true if you want indentation. false if not. Default is false
     * @return poet
     */
    fun indent(indent: Boolean): ResourcesPoet {
        this.indent = indent
        return this
    }

    /**
     * Build the XML to a string
     *
     * @return the xml as a string
     */
    fun build(): String {
        val writer = StringWriter()
        val result = StreamResult(writer)
        build(result)
        return writer.toString()
    }

    /**
     * Build the XML to a [ByteArray]
     *
     * @return the xml as a UTF-8 byte array
     */
    fun buildBytes(): ByteArray {
        val writer = ByteArrayOutputStream()
        build(writer)
        return writer.toByteArray()
    }

    /**
     * Build the XML to a file. You should call [File.createNewFile] or validate that the file exists
     * before calling
     *
     * @param file the file to output the XML to
     */
    fun build(file: File) {
        val result = StreamResult(file)
        build(result)
    }

    /**
     * Build the XML to the [OutputStream]
     *
     * @param outputStream the output stream to output the XML to
     */
    fun build(outputStream: OutputStream) {
        val result = StreamResult(outputStream)
        build(result)
    }

    /**
     * Build the XML to the [Writer]
     *
     * @param writer the writer to output the XML to
     */
    fun build(writer: Writer) {
        val result = StreamResult(writer)
        build(result)
    }

    /**
     * Build the XML to the [StreamResult]
     *
     * @param result the result
     */
    private fun build(result: StreamResult) {
        try {
            val transformer = transformerFactory.newTransformer()
            transformer.setOutputProperty(OutputKeys.ENCODING, "utf-8")
            if (indent) {
                transformer.setOutputProperty(OutputKeys.INDENT, "yes")
                transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4")
            }
            val source = DOMSource(document)
            transformer.transform(source, result)
        } catch (e: Exception) {
            throw RuntimeException(
                "Something is seriously wrong with the ResourcePoet configuration. Cannot build the result",
                e
            )
        }
    }
}

package com.commit451.resourcespoet

import org.w3c.dom.Document
import org.w3c.dom.Element
import org.xml.sax.SAXException
import java.io.*
import java.util.*
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
@Suppress("MemberVisibilityCanBePrivate", "unused")
class ResourcesPoet private constructor(
    private val document: Document,
    private val resourceElement: Element,
    private var indent: Boolean = INDENT_DEFAULT
) {

    companion object {

        enum class ELEMENT constructor(val elementName: String) {
            RESOURCES("resources"),
            FONT_FAMILIES("font-family")
        }

        private const val INDENT_DEFAULT = false

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
        @JvmStatic
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
         * @return poet
         */
        @JvmStatic
        fun create(file: File, indent: Boolean = INDENT_DEFAULT): ResourcesPoet {
            try {
                return create(FileInputStream(file), indent)
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
        @JvmStatic
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

    /**
     * Add an attr to the XML file
     *
     * @param attr the defined attribute
     * @return poet
     */
    fun addAttr(attr: Attr): ResourcesPoet {
        //<attr name="gravityX" format="float"/>
        val element = document.createElement(Type.ATTR.toString())
        element.setAttribute("name", attr.name)
        if (!attr.formats.isEmpty()) {
            var formatString = ""
            for (format in attr.formats) {
                formatString = formatString + format.toString() + "|"
            }
            //remove last |
            formatString = formatString.substring(0, formatString.length - 1)
            element.setAttribute("format", formatString)
        }
        resourceElement.appendChild(element)
        return this
    }

    /**
     * Add a boolean to the XML file
     *
     * @param name  the name
     * @param value the value
     * @return poet
     */
    fun addBool(name: String, value: Boolean): ResourcesPoet {
        addBool(name, value.toString())
        return this
    }

    /**
     * Add a boolean to the XML file
     *
     * @param name  the name
     * @param value the value
     * @return poet
     */
    fun addBool(name: String, value: String): ResourcesPoet {
        //<bool name="is_production">false</bool>
        val element = document.createElement(Type.BOOL.toString())
        element.setAttribute("name", name)
        element.appendChild(document.createTextNode(value))
        resourceElement.appendChild(element)
        return this
    }

    /**
     * Add a color to the XML file
     *
     * @param name  the name
     * @param value the value
     * @return poet
     */
    fun addColor(name: String, value: String): ResourcesPoet {
        //<color name="color_primary">#7770CB</color>
        val element = document.createElement(Type.COLOR.toString())
        element.setAttribute("name", name)
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
     * @return poet
     */
    fun addDrawable(name: String, value: String): ResourcesPoet {
        //<drawable name="logo">@drawable/logo</drawable>
        val bool = document.createElement(Type.DRAWABLE.toString())
        bool.setAttribute("name", name)
        bool.appendChild(document.createTextNode(value))
        resourceElement.appendChild(bool)
        return this
    }

    /**
     * Add a dimension to the XML file
     *
     * @param name  the name
     * @param value the value
     * @return poet
     */
    fun addDimension(name: String, value: String): ResourcesPoet {
        //<drawable name="logo">@drawable/logo</drawable>
        val bool = document.createElement(Type.DIMENSION.toString())
        bool.setAttribute("name", name)
        bool.appendChild(document.createTextNode(value))
        resourceElement.appendChild(bool)
        return this
    }

    /**
     * Add an id to the XML file
     *
     * @param id the id
     * @return poet
     */
    fun addId(id: String): ResourcesPoet {
        //        <item
        //                type="id"
        //        name="id_name" />
        val bool = document.createElement(Type.ID.toString())
        bool.setAttribute("name", id)
        bool.setAttribute("type", "id")
        resourceElement.appendChild(bool)
        return this
    }

    /**
     * Add an integer to the XML file
     *
     * @param name  the name
     * @param value the value
     * @return poet
     */
    fun addInteger(name: String, value: Int?): ResourcesPoet {
        addInteger(name, value.toString())
        return this
    }

    /**
     * Add an integer to the XML file
     *
     * @param name  the name
     * @param value the value
     * @return poet
     */
    fun addInteger(name: String, value: String): ResourcesPoet {
        //<drawable name="logo">@drawable/logo</drawable>
        val bool = document.createElement(Type.INTEGER.toString())
        bool.setAttribute("name", name)
        bool.appendChild(document.createTextNode(value))
        resourceElement.appendChild(bool)
        return this
    }

    /**
     * Add an integer array to the XML file
     *
     * @param name   the name
     * @param values the value
     * @return poet
     */
    fun addIntegerArray(name: String, values: List<Int>): ResourcesPoet {
        val integers = ArrayList<String>()
        for (value in values) {
            integers.add(value.toString())
        }
        addIntegerArrayStrings(name, integers)
        return this
    }

    /**
     * Add an integer array to the XML file
     *
     * @param name   the name
     * @param values the value
     * @return poet
     */
    fun addIntegerArrayStrings(name: String, values: List<String>): ResourcesPoet {
        // <integer-array name="numbers">
        //      <item>0</item>
        //      <item>1</item>
        // </integer-array>
        val element = document.createElement(Type.INTEGER_ARRAY.toString())
        element.setAttribute("name", name)
        for (value in values) {
            //Does this mess up the ordering?
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
     * @return poet
     */
    fun addPlurals(name: String, plurals: List<Plural>): ResourcesPoet {
        //    <plurals name="numberOfSongsAvailable">
        //        <item quantity="one">Znaleziono %d piosenkę.</item>
        //        <item quantity="few">Znaleziono %d piosenki.</item>
        //        <item quantity="other">Znaleziono %d piosenek.</item>
        //    </plurals>
        val element = document.createElement(Type.PLURALS.toString())
        element.setAttribute("name", name)
        for (plural in plurals) {
            //Does this mess up the ordering?
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
     * @return poet
     */
    @JvmOverloads
    fun addString(name: String, value: String, translatable: Boolean = true): ResourcesPoet {
        //<string name="app_name" translatable="false">Cool</string>
        val element = document.createElement(Type.STRING.toString())
        element.setAttribute("name", name)
        if (!translatable) {
            element.setAttribute("translatable", "false")
        }
        element.appendChild(document.createTextNode(value))
        resourceElement.appendChild(element)
        return this
    }

    /**
     * Add a String array to the XML file
     *
     * @param name   the name
     * @param values the value
     * @return poet
     */
    fun addStringArray(name: String, values: List<String>): ResourcesPoet {
        //<string-array name="country_names">
        //      <item>Country</item>
        //      <item>United States</item>
        // </string-array>
        val element = document.createElement(Type.STRING_ARRAY.toString())
        element.setAttribute("name", name)
        for (value in values) {
            //Does this mess up the ordering?
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
     * @return poet
     */
    @JvmOverloads
    fun addStyle(name: String, parentRef: String? = null, styleItems: List<StyleItem>? = null): ResourcesPoet {
        //<style name="AppTheme.Dark" parent="Base.AppTheme.Dark"/>
        val element = document.createElement(Type.STYLE.toString())
        element.setAttribute("name", name)
        if (parentRef != null) {
            element.setAttribute("parent", parentRef)
        }
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
     * @return poet
     */
    fun addTypedArray(name: String, values: List<String>): ResourcesPoet {
        //<array name="country_names">
        //      <item>Country</item>
        //      <item>United States</item>
        // </array>
        val element = document.createElement(Type.TYPED_ARRAY.toString())
        element.setAttribute("name", name)
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
     * @return poet
     */
    fun addFontFamily(fontFamily: FontFamily): ResourcesPoet {
        val element = document.createElement(Type.FONT.toString())
        element.setAttribute("android:fontStyle", fontFamily.fontStyle)
        element.setAttribute("android:fontWeight", fontFamily.fontWeight)
        element.setAttribute("android:font", fontFamily.font)
        resourceElement.appendChild(element)
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
     * Any other type will throw an [IllegalArgumentException], as they have a special configuration
     *
     * @param type the type of the resource you wish to add
     * @param name the name of the element
     * @param value the value of the element
     * @return poet
     */
    fun add(type: Type, name: String, value: String): ResourcesPoet {
        return when (type) {
            Type.BOOL -> addBool(name, value)
            Type.COLOR -> addColor(name, value)
            Type.DIMENSION -> addDimension(name, value)
            Type.DRAWABLE -> addDrawable(name, value)
            Type.INTEGER -> addInteger(name, value)
            Type.STRING -> addString(name, value)
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

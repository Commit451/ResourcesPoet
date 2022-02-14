# ResourcePoet
Kotlin API for generating Android XML Resources

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.commit451/ResourcesPoet/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.commit451/ResourcesPoet)

## Gradle
```groovy
dependencies {
    implementation("com.commit451:resourcespoet:latest.release.here")
}
```

## Basic Usage
Write variables to the poet like:
```kotlin
val poet = ResourcesPoet.create()
    .addString("app_name", "Test")
    .addColor("color_primary", "#FF0000")
    .addBool("is_cool", true)
    .addComment("This is a comment")
    .addDrawable("logo", "@drawable/logo")
    .addStyle("AppTheme.Dark", "Base.AppTheme.Dark")
    // etc
    .indent(true)
val xml: String = poet.build()
println(xml)
```
which would output this XML:
```xml
<?xml version="1.0" encoding="utf-8" standalone="no"?>
<resources>
    <string name="app_name">Test</string>
    <color name="color_primary">#FF0000</color>
    <bool name="is_cool">true</bool>
    <!--This is a comment-->
    <drawable name="logo">@drawable/logo</drawable>
    <style name="AppTheme.Dark" parent="Base.AppTheme.Dark"/>
</resources>
```

To get the XML result as a file:
```kotlin
val valuesFolder = File(resFolderPath + File.separator + "values")
valuesFolder.mkdirs()
val configXml = File(valuesFolder, "config.xml")
configXml.createNewFile()
poet.build(configXml)
```

You can even start with and modify an existing resource file:
```kotlin
val file = File("some/path/to/file")
val poet = ResourcesPoet.create(file)
    .remove(Type.STRING, "app_name")
    .addString("app_name", "Even Better App Name")
    .add(Type.BOOL, "is_cool", "true")
```

## Supported Types
Most [resource types](https://developer.android.com/guide/topics/resources/available-resources.html) are supported. All look similar in usage:
```kotlin
val poet = ResourcesPoet.create()
    .addBool("is_cool", true)
    .addColor("color_primary", "#FF0000")
    .addComment("This is a comment")
    .addDimension("margin", "2dp")
    .addDrawable("logo", "@drawable/logo")
    .addId("some_id")
    .addInteger("number", 0)
    .addIntegerArray("numbers", numbers)
    .addPlurals("songs", plurals)
    .addString("app_name", "Test")
    .addStringArray("stuff", strings)
    .addStyle("AppTheme.Dark", "Base.AppTheme.Dark")
    .addTypedArray("some_typed_array", typedArray)
```
We do not allow configuration of more complicated resources like `drawable` and `anim` in the creation sense.

License
--------

    Copyright 2022 Commit 451

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

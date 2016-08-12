# ResourcePoet
Like [JavaPoet](https://github.com/square/javapoet), but for Android XML Resources

[![Build Status](https://travis-ci.org/Commit451/ResourcesPoet.svg?branch=master)](https://travis-ci.org/Commit451/ResourcesPoet)
[![](https://jitpack.io/v/Commit451/ResourcesPoet.svg)](https://jitpack.io/#Commit451/ResourcesPoet)

# Gradle
```groovy
repositories {
    jcenter()
    maven { url "https://jitpack.io" }
}
dependencies {
    compile 'com.github.Commit451:ResourcesPoet:0.0.1'
}
```

# Basic Usage
Write variables to the poet like:
```java
ResourcesPoet poet = ResourcesPoet.create()
            .addString("app_name", "Test")
            .addColor("color_primary", "#FF0000")
            .addBool("is_cool", true).addComment("This is a comment")
            .addDrawable("logo", "@drawable/logo")
            .addStyle("AppTheme.Dark", "Base.AppTheme.Dark");
            //etc
```
When you are ready for the XML result as a file:
```java
File valuesFolder = new File(resFolderPath + File.separator + "values");
valuesFolder.mkdirs();
File configXml = new File(valuesFolder, "config.xml");
configXml.createNewFile();
StreamResult sr = new StreamResult(configXml);
xmlResourcesBuilder.build(sr, true);
```
or if you want to write it to a string:
```java
StringWriter writer = new StringWriter();
StreamResult result = new StreamResult(writer);
poet.build(result, true);

String resourcesXml = writer.toString();
```

## Supported Types
Most [resource types](https://developer.android.com/guide/topics/resources/available-resources.html) are supported. All look similar in usage:
```java
ResourcesPoet poet = ResourcesPoet.create()
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
            .addTypedArray("some_typed_array", typedArray);
```
We do not allow configuration of more complicated resources like `style` and `drawable` and `anim` in the creation sense. Maybe one day

License
--------

    Copyright 2016 Commit 451

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

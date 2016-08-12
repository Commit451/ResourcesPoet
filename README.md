# ResourcePoet
Like JavaPoet, but for Android XML Resources

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
```java
ResourcesPoet poet = ResourcesPoet.create();
poet.addString("app_name", "Test");
poet.addColor("color_primary", "#FF0000");
poet.addBool("is_cool", true);
poet.addComment("This is a comment");

//This writes the result to a string, in a pretty format
StringWriter writer = new StringWriter();
StreamResult result = new StreamResult(writer);
poet.build(result, true);
```

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

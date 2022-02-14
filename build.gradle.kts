buildscript {
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
    dependencies {
        classpath("com.vanniktech:gradle-maven-publish-plugin:0.18.0")
    }
}

plugins {
    kotlin("jvm") version "1.6.10"
    id("com.github.ben-manes.versions") version "0.42.0"
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(11))
    }
}

apply(plugin = "com.vanniktech.maven.publish")

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("junit:junit:4.13.2")
    testImplementation("com.google.guava:guava:31.0.1-jre")
}

apply(from = "sonatype.gradle")

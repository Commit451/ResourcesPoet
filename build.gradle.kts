import com.jfrog.bintray.gradle.BintrayExtension
import org.jetbrains.dokka.gradle.DokkaTask
import org.gradle.jvm.tasks.Jar

plugins {
    `build-scan`
    `maven-publish`
    kotlin("jvm") version "1.3.10"
    id("org.jetbrains.dokka") version "0.9.17"
    id("com.jfrog.bintray") version "1.8.4"
    id("com.github.ben-manes.versions") version "0.20.0"
}

group = "com.commit451"
version = "2.1.0"

repositories {
    jcenter()
}

dependencies {
    implementation(kotlin("stdlib", "1.3.10"))
    testImplementation("junit:junit:4.12")
    testImplementation("com.google.guava:guava:27.0.1-jre")
}

val dokka by tasks.getting(DokkaTask::class) {
    outputFormat = "html"
    outputDirectory = "$buildDir/javadoc"
}

val dokkaJar by tasks.creating(org.gradle.api.tasks.bundling.Jar::class) {
    group = JavaBasePlugin.DOCUMENTATION_GROUP
    description = "Assembles Kotlin docs with Dokka"
    classifier = "javadoc"
    from(dokka)
}

publishing {
    publications {
        create("default", MavenPublication::class.java) {
            from(components["java"])
            artifact(dokkaJar)
        }
    }
}

bintray {
    user = if (project.hasProperty("bintrayUser")) project.property("bintrayUser") as String else System.getenv("BINTRAY_USER")
    key = if (project.hasProperty("bintrayApiKey")) project.property("bintrayApiKey") as String else System.getenv("BINTRAY_API_KEY")
    override = true
    publish = true
    setPublications("default")
    pkg(delegateClosureOf<BintrayExtension.PackageConfig> {
        repo = "maven"
        name = "resourcespoet"
        description = "Kotlin API for generating Android XML Resources"
        publish = true
        vcsUrl = "https://github.com/Commit451/ResourcesPoet.git"
        dryRun = false
    })
}

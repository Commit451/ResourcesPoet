import com.jfrog.bintray.gradle.BintrayExtension
import org.jetbrains.dokka.gradle.DokkaTask
import org.gradle.jvm.tasks.Jar

plugins {
    `build-scan`
    `maven-publish`
    kotlin("jvm") version "1.2.61"
    id("org.jetbrains.dokka") version "0.9.16"
    id("com.jfrog.bintray") version "1.8.4"
}

group = "com.commit451"
version = "2.1.0"

repositories {
    jcenter()
}

dependencies {
    implementation(kotlin("stdlib", "1.2.61"))
    testImplementation("junit:junit:4.12")
    testImplementation("com.google.guava:guava:26.0-jre")
}

// Configure existing Dokka task to output HTML to typical Javadoc directory
val dokka by tasks.getting(DokkaTask::class) {
    outputFormat = "html"
    outputDirectory = "$buildDir/javadoc"
}

// Create dokka Jar task from dokka task output
val dokkaJar by tasks.creating(org.gradle.api.tasks.bundling.Jar::class) {
    group = JavaBasePlugin.DOCUMENTATION_GROUP
    description = "Assembles Kotlin docs with Dokka"
    classifier = "javadoc"
    // dependsOn(dokka) not needed; dependency automatically inferred by from(dokka)
    from(dokka)
}

// Create sources Jar from main kotlin sources
val sourcesJar by tasks.creating(org.gradle.api.tasks.bundling.Jar::class) {
    group = JavaBasePlugin.DOCUMENTATION_GROUP
    description = "Assembles sources JAR"
    classifier = "sources"
    from(java.sourceSets["main"].allSource)
}

publishing {
    publications {
        create("default", MavenPublication::class.java) {
            from(components["java"])
            artifact(sourcesJar)
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

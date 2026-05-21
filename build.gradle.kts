import com.vanniktech.maven.publish.JavaLibrary
import com.vanniktech.maven.publish.JavadocJar

plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.vanniktech.publish)
}

group = findProperty("GROUP") as String
version = findProperty("VERSION_NAME") as String

kotlin {
    jvmToolchain(21)
}

dependencies {
    testImplementation(libs.junit)
}

mavenPublishing {
    configure(
        JavaLibrary(
            javadocJar = JavadocJar.Javadoc(),
            sourcesJar = true,
        )
    )
    publishToMavenCentral(automaticRelease = true)
    if (System.getenv("RELEASE_SIGNING_ENABLED") == "true") {
        signAllPublications()
    }
}

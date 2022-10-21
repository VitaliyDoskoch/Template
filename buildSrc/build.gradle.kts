import java.io.*
import java.util.*

val classpathProperties = Properties().apply {
    load(FileInputStream(File(rootDir.parentFile, "classpath.properties")))
}

plugins {
    `kotlin-dsl`
    `kotlin-dsl-precompiled-script-plugins`
}

repositories {
    google()
    mavenCentral()
    gradlePluginPortal()
}

dependencies {
    implementation(classpathProperties.getProperty("android"))
    implementation(classpathProperties.getProperty("kotlin"))
}
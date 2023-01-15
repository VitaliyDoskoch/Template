import java.io.*
import java.util.*

val plugins = Properties().apply { load(FileInputStream(File(rootDir, "plugin.properties"))) }

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
    implementation("com.android.tools.build:gradle:${plugins["android"]}")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:${plugins["kotlin"]}")
//    implementation("com.google.dagger:hilt-android-gradle-plugin:${plugins["hilt"]}")
}
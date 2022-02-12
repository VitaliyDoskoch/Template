import java.io.FileInputStream
import java.util.*

plugins {
    id("com.android.application")
    id("androidx.navigation.safeargs")
    id("kotlin-android")
    id("kotlin-kapt")
}

apply(from = "$rootDir/android.gradle")

val properties = Properties()
properties.load(FileInputStream(rootProject.file("local.properties")))

android {
    signingConfigs {
        create("config") {
            keyAlias = properties["keyAlias"] as? String
            keyPassword = properties["keyPassword"] as? String
            storeFile = file(properties["storeFile"] as Any)
            storePassword = properties["storePassword"] as? String
        }
    }

    defaultConfig {
        applicationId = "com.doskoch.movies"
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
        }
        release {
            isMinifyEnabled = true
            isShrinkResources = true

            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
}

dependencies {
    implementation(fileTree("dir" to "libs", "include" to listOf("*.jar")))

    implementation(project(":core"))

    implementation(project(":apis:the_movie_db"))

    implementation(project(":features:splash"))

    debugImplementation(libraries.getValue("leakCanary"))

    kapt(processors.getValue("glide"))
}

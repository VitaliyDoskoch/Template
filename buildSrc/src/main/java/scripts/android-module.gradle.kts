import com.android.build.gradle.LibraryExtension
import com.android.build.gradle.internal.dsl.BaseAppModuleExtension

plugins {
    id("kotlin-android")
    id("kotlin-kapt")
}

android {
    val maxSdk = 33

    when(this) {
        is BaseAppModuleExtension -> {
            compileSdk = maxSdk

            defaultConfig {
                applicationId = "io.spilno.android"
                versionCode = 1
                versionName = "1.0.0"
            }
        }
        is LibraryExtension -> {
            compileSdk = maxSdk
        }
    }

    defaultConfig {
        minSdk = 24
        targetSdk = maxSdk

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    buildTypes {
        getByName("debug") {
            buildConfigField("boolean", "isLoggingEnabled", "true")
        }
        getByName("release") {
            buildConfigField("boolean", "isLoggingEnabled", "true")
        }
    }

    flavorDimensions("build")

    productFlavors {
        /**
         * It's a lightweight variant that is designed for faster builds.
         */
        create("light") {
            dimension = "build"
            resourceConfigurations.addAll(setOf("en", "xxhdpi", "port"))
            ndk { abiFilters.add("x86") }
            splits.abi.isEnable = false
            splits.density.isEnable = false
        }
        create("normal") {
            dimension = "build"
        }
    }
}

dependencies {
    implementation(fileTree("dir" to "libs", "include" to listOf("*.jar")))

    implementation(Dependencies.kotlin)
    implementation(Dependencies.coroutines)
}
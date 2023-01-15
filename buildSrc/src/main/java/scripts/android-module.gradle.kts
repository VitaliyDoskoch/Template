import com.android.build.gradle.LibraryExtension
import com.android.build.gradle.internal.dsl.BaseAppModuleExtension

plugins {
    id("kotlin-android")
    id("kotlin-kapt")
    id("ktlint")
//    id("dagger.hilt.android.plugin")
}

android {
    val maxSdk = 33

    when(this) {
        is BaseAppModuleExtension -> compileSdk = maxSdk
        is LibraryExtension -> compileSdk = maxSdk
    }

    defaultConfig {
        minSdk = 24
        targetSdk = maxSdk

        if(this@android is BaseAppModuleExtension) {
            applicationId = "com.doskoch.template"
            versionCode = 1
            versionName = "1.0.0"
        }

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
}

dependencies {
    implementation(fileTree("dir" to "libs", "include" to listOf("*.jar")))

    implementation(Libraries.kotlin)
    implementation(Libraries.coroutines)

    implementation(Libraries.timber)

//    implementation(Libraries.hilt)
//    kapt(Libraries.hiltCompiler)
}
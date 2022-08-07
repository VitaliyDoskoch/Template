plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
}

apply(from = "$rootDir/android.gradle")
apply(from = "$rootDir/compose.gradle")

android {
    defaultConfig {
        applicationId = "com.doskoch.template"
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

    implementation(project(":api"))
    implementation(project(":database"))
    implementation(project(":core"))

    implementation(project(":features:splash"))
    implementation(project(":features:authorization"))
    implementation(project(":features:anime"))

    debugImplementation(Dependencies.leakCanary)

    implementation(Dependencies.fragmentKtx)
    implementation(Dependencies.navigationKtx)
}

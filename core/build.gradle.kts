plugins {
    id("com.android.library")
}

apply(from = "$rootDir/android.gradle")

android {
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Versions.compose
    }
}

dependencies {
    implementation(fileTree("dir" to "libs", "include" to listOf("*.jar")))

    api (project(":api"))
    api (project(":database"))
    api (project(":legacy"))

    api(Dependencies.timber)
    api(Dependencies.coreKtx)
    api(Dependencies.material)
    api(Dependencies.compose)
}

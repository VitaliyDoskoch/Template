plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
}

apply(from = "$rootDir/android.gradle")

dependencies {
    implementation(fileTree("dir" to "libs", "include" to listOf("*.jar")))

    implementation(libraries.getValue("roomRuntime"))
    implementation(libraries.getValue("roomRxJava2"))
    kapt(processors.getValue("roomCompiler"))
}

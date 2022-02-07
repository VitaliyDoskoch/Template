plugins {
    id("com.android.library")
}

apply(from = "$rootDir/android.gradle")

dependencies {
    implementation(fileTree("dir" to "libs", "include" to listOf("*.jar")))

    implementation(libraries.getValue("retrofit2"))
    implementation(libraries.getValue("retrofit2AdapterRxJava2"))
}
plugins {
    id("com.android.library")
}

apply(from = "$rootDir/android.gradle")

dependencies {
    implementation(fileTree("dir" to "libs", "include" to listOf("*.jar")))

    implementation (libraries.getValue("lifecycleExtensions"))

    implementation (testLibraries.getValue("kotlinTestJunit"))
    implementation (testLibraries.getValue("mockk"))
}

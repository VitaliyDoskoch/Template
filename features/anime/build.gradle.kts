plugins {
    id("com.android.library")
}

apply(from = "$rootDir/android.gradle")
apply(from = "$rootDir/compose.gradle")

dependencies {
    implementation(fileTree("dir" to "libs", "include" to listOf("*.jar")))

    implementation(project(":core"))
}
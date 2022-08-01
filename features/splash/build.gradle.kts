plugins {
    id("com.android.library")
}

apply(from = "$rootDir/feature.gradle")

dependencies {
    implementation(fileTree("dir" to "libs", "include" to listOf("*.jar")))

    implementation(project(":core"))
}

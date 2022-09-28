plugins {
    id("com.android.library")
    id("feature")
}

dependencies {
    implementation(fileTree("dir" to "libs", "include" to listOf("*.jar")))

    implementation(project(":core"))
}
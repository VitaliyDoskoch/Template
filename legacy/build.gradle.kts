plugins {
    id("com.android.library")
}
apply(from = "$rootDir/android.gradle")

android {

}

dependencies {
    implementation(fileTree("dir" to "libs", "include" to listOf("*.jar")))
}

plugins {
    id("com.android.library")
}
apply(from = "$rootDir/android.gradle")

dependencies {
    implementation(fileTree("dir" to "libs", "include" to listOf("*.jar")))

    implementation("androidx.core:core-ktx:1.2.0")
    implementation ("androidx.appcompat:appcompat:1.1.0")
    implementation ("com.google.android.material:material:1.2.0-alpha03")
    implementation ("androidx.recyclerview:recyclerview:1.1.0")
}

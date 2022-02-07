plugins {
    id("com.android.library")
}

apply(from = "$rootDir/android.gradle")

android {
    viewBinding.isEnabled = true
}

dependencies {
    implementation(fileTree("dir" to "libs", "include" to listOf("*.jar")))

    implementation (project(":core"))

    testImplementation (project(":extensions:lifecycle_test"))

    androidTestImplementation (project(":extensions:android_test"))
    androidTestImplementation ((rootProject.extra["testLibraries"] as Map<String, String>)["testFragments"]!!)
    androidTestImplementation ((rootProject.extra["testLibraries"] as Map<String, String>)["espressoCore"]!!)
}

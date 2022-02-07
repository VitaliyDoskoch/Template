plugins {
    id("com.android.library")
}

apply(from = "$rootDir/android.gradle")

dependencies {
    implementation(fileTree("dir" to "libs", "include" to listOf("*.jar")))

    implementation (libraries.getValue("appCompat"))
    implementation (libraries.getValue("material"))
    implementation (libraries.getValue("swipeRefreshLayout"))

    implementation (testLibraries.getValue("testCore"))
    implementation (testLibraries.getValue("hamcrest"))

    implementation (testLibraries.getValue("espressoCore"))
    implementation (testLibraries.getValue("espressoIntents"))
}

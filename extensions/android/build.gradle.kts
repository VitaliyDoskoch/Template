plugins {
    id("com.android.library")
}

apply(from = "$rootDir/android.gradle")

dependencies {
    implementation(fileTree("dir" to "libs", "include" to listOf("*.jar")))

    implementation (libraries.getValue("coreKtx"))

    implementation (libraries.getValue("appCompat"))
    implementation (libraries.getValue("material"))
}

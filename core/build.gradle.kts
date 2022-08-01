plugins {
    id("com.android.library")
}

apply(from = "$rootDir/android.gradle")

dependencies {
    implementation(fileTree("dir" to "libs", "include" to listOf("*.jar")))

    api (project(":api"))
    api (project(":database"))
    api (project(":legacy"))

    api(Dependencies.timber)
    api(Dependencies.material)
    api(Dependencies.coreKtx)
}

plugins {
    id("com.android.library")
}

apply(from = "$rootDir/android.gradle")
apply(from = "$rootDir/compose.gradle")

dependencies {
    implementation(fileTree("dir" to "libs", "include" to listOf("*.jar")))

    api(project(":legacy"))

    implementation(project(":api"))
    implementation(project(":database"))

    api(Dependencies.timber)
    api(Dependencies.coreKtx)
    api(Dependencies.material)
    api(Dependencies.compose)
    api(Dependencies.accompanist)
    api(Dependencies.coil)
    api(Dependencies.paging)
    api(Dependencies.pagingCompose)

    implementation(Dependencies.dataStore)
}

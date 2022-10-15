plugins {
    id("android-module")
    id("compose")
}

dependencies {
    implementation(project(":api"))
    implementation(project(":database"))
    implementation(project(":core"))
    implementation(project(":legacy"))

    implementation(Dependencies.timber)
    implementation(Dependencies.room)
    implementation(Dependencies.dataStore)
}
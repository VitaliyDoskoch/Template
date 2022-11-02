plugins {
    id("com.android.library")
    id("android-module")
    id("compose")
}

dependencies {
    implementation(project(":api"))
    implementation(project(":database"))
    implementation(project(":core"))
    implementation(project(":legacy"))

    implementation(Libraries.timber)
    implementation(Libraries.room)
    implementation(Libraries.dataStore)
}
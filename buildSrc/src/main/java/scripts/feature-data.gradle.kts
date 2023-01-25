plugins {
    id("com.android.library")
    id("android-module")
}

dependencies {
    implementation(project(":core:android"))
    implementation(project(":core:kotlin"))
    implementation(project(":api"))
    implementation(project(":database"))
}
plugins {
    id("com.android.library")
    id("android-module")
    id("compose")
}

dependencies {
    implementation(project(":core:android"))
    implementation(project(":core:kotlin"))
    implementation(project(":core:domain"))
}
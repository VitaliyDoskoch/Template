plugins {
    id("com.android.library")
    id("android-module")
}

android {
    namespace = "com.doskoch.template.core.data"
}

dependencies {
    implementation(project(":core:domain"))

    implementation(Libraries.dataStore)
}
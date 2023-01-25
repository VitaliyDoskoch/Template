plugins {
    id("com.android.library")
    id("android-module")
    id("compose")
}

android {
    namespace = "com.doskoch.template.core.android"
}

dependencies {
    implementation(project(":core:kotlin"))

    implementation(Libraries.material)

    compileOnly(Libraries.gson)   //for JsonNavType
}
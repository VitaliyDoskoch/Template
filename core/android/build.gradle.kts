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

    implementation(Libraries.gson)   //for JsonNavType
    compileOnly(Libraries.retrofit2) //for HttpException
}
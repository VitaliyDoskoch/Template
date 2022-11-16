plugins {
    id("com.android.library")
    id("android-module")
    id("compose")
}

android {
    namespace = "com.doskoch.template.core"
}

dependencies {
    implementation(project(":api"))

    implementation(Libraries.material)
    
    implementation(Libraries.gson)   //for JsonNavType
    compileOnly(Libraries.retrofit2) //for HttpException

    implementation(Libraries.dataStore)
}

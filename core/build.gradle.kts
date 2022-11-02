plugins {
    id("com.android.library")
    id("android-module")
    id("compose")
}

dependencies {
    implementation(project(":api"))
    implementation(project(":legacy"))

    implementation(Libraries.material)
    
    implementation(Libraries.gson)   //for JsonNavType
    compileOnly(Libraries.retrofit2) //for HttpException

    implementation(Libraries.dataStore)
}

plugins {
    id("com.android.library")
    id("android-module")
    id("compose")
}

dependencies {
    implementation(project(":api"))
    implementation(project(":legacy"))

    implementation(Dependencies.timber)
    implementation(Dependencies.material)
    
    implementation(Dependencies.gson)   //for JsonNavType
    compileOnly(Dependencies.retrofit2) //for HttpException

    implementation(Dependencies.dataStore)
}

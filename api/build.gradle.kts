plugins {
    id("com.android.library")
    id("android-module")
}

dependencies {
    implementation(Dependencies.gson)
    implementation(Dependencies.retrofit2)
    implementation(Dependencies.retrofit2ConverterGson)
    implementation(Dependencies.okhttpLoggingInterceptor)

    implementation(Dependencies.timber)
    implementation(Dependencies.annotations)
}
plugins {
    id("com.android.library")
    id("android-module")
}

dependencies {
    implementation(Libraries.gson)
    implementation(Libraries.retrofit2)
    implementation(Libraries.retrofit2ConverterGson)
    implementation(Libraries.okhttpLoggingInterceptor)
    implementation(Libraries.annotations)
}

android {
    namespace = "com.doskoch.template.api"
}
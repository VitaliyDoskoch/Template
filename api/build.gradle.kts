plugins {
    id("com.android.library")
}

apply(from = "$rootDir/android.gradle")

dependencies {
    implementation(fileTree("dir" to "libs", "include" to listOf("*.jar")))

    api(Dependencies.gson)
    api(Dependencies.retrofit2)
    implementation(Dependencies.retrofit2ConverterGson)
    implementation(Dependencies.okhttpLoggingInterceptor)

    implementation(Dependencies.timber)
    implementation(Dependencies.annotations)
}
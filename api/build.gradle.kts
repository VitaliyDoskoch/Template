import java.io.FileInputStream
import java.util.*

plugins {
    id("com.android.library")
}

apply(from = "$rootDir/android.gradle")

val properties = Properties()
properties.load(FileInputStream(rootProject.file("local.properties")))

android {
    defaultConfig {
        buildConfigField("String", "the_movie_db_api_key", properties["the_movie_db_api_key"] as String)
    }
}

dependencies {
    implementation(fileTree("dir" to "libs", "include" to listOf("*.jar")))

    implementation(Dependencies.timber)
    implementation(Dependencies.annotations)

    implementation(Dependencies.retrofit2)
    implementation(Dependencies.retrofit2ConverterGson)
    implementation(Dependencies.retrofit2AdapterRxJava2)
    implementation(Dependencies.okhttpLoggingInterceptor)
    implementation(Dependencies.gson)
}
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

    implementation (libraries.getValue("retrofit2ConverterGson"))
    implementation (libraries.getValue("retrofit2AdapterRxJava2"))

    implementation (libraries.getValue("okhttpLoggingInterceptor"))

    implementation (libraries.getValue("gson"))

    implementation (libraries.getValue("rxAndroid"))
    implementation (libraries.getValue("rxJava"))

    implementation (libraries.getValue("timber"))

    compileOnly (libraries.getValue("annotations"))
}
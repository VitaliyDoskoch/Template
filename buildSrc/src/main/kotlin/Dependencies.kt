object Versions {
    const val kotlin = "1.6.10"
    const val coroutines = "1.3.9"

    const val timber = "4.6.1"
    const val leakCanary = "2.2"

    const val material = "1.2.0-alpha03"

    const val coreKtx = "1.2.0"
    const val annotations = "1.0.0"
    const val navigationFragmentKtx = "2.2.1"

    const val room = "2.2.5"

    const val retrofit2 = "2.8.1"
    const val okhttpLoggingInterceptor = "4.2.1"
    const val gson = "2.8.6"
}

object Dependencies {
    const val kotlin = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Versions.kotlin}"
    const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"

    const val timber = "com.jakewharton.timber:timber:${Versions.timber}"
    const val leakCanary = "com.squareup.leakcanary:leakcanary-android:${Versions.leakCanary}"

    const val material = "com.google.android.material:material:${Versions.material}"

    const val coreKtx = "androidx.core:core-ktx:${Versions.coreKtx}"
    const val annotations = "androidx.annotation:annotation:${Versions.annotations}"
    const val navigationFragmentKtx = "androidx.navigation:navigation-fragment-ktx:${Versions.navigationFragmentKtx}"

    const val room = "androidx.room:room-ktx:${Versions.room}"
    const val roomRxJava2 = "androidx.room:room-rxjava2:${Versions.room}"
    const val roomCompiler = "androidx.room:room-compiler:${Versions.room}"

    const val retrofit2 = "com.squareup.retrofit2:retrofit:${Versions.retrofit2}"
    const val retrofit2ConverterGson = "com.squareup.retrofit2:converter-gson:${Versions.retrofit2}"
    const val okhttpLoggingInterceptor = "com.squareup.okhttp3:logging-interceptor:${Versions.okhttpLoggingInterceptor}"
    const val gson = "com.google.code.gson:gson:${Versions.gson}"
}

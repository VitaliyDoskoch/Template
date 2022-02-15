object Versions {
    const val kotlin = "1.6.10"
    const val coroutines = "1.3.9"

    const val coreKtx = "1.2.0"

    const val recyclerView = "1.1.0"
    const val swipeRefreshLayout = "1.0.0"

    const val room = "2.2.5"

    const val retrofit2 = "2.8.1"
    const val okhttpLoggingInterceptor = "4.2.1"
    const val gson = "2.8.6"

    const val timber = "4.6.1"
}

object Dependencies {
    const val kotlin = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Versions.kotlin}"
    const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"

    const val coreKtx = "androidx.core:core-ktx:${Versions.coreKtx}"

    const val recyclerView = "androidx.recyclerview:recyclerview:${Versions.recyclerView}"
    const val swipeRefreshLayout = "androidx.swiperefreshlayout:swiperefreshlayout:${Versions.swipeRefreshLayout}"

    const val room = "androidx.room:room-ktx:${Versions.room}"
    const val roomRxJava2 = "androidx.room:room-rxjava2:${Versions.room}"

    const val retrofit2 = "com.squareup.retrofit2:retrofit:${Versions.retrofit2}"
    const val retrofit2AdapterRxJava2 = "com.squareup.retrofit2:adapter-rxjava2:${Versions.retrofit2}"
    const val retrofit2ConverterGson = "com.squareup.retrofit2:converter-gson:${Versions.retrofit2}"
    const val okhttpLoggingInterceptor = "com.squareup.okhttp3:logging-interceptor:${Versions.okhttpLoggingInterceptor}"
    const val gson = "com.google.code.gson:gson:${Versions.gson}"

    const val timber = "com.jakewharton.timber:timber:${Versions.timber}"
}
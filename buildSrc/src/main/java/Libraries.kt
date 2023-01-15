object Libraries {
    const val kotlin = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Versions.kotlin}"
    const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"

    const val hilt = "com.google.dagger:hilt-android:${Versions.hilt}"
    const val hiltCompiler = "com.google.dagger:hilt-android-compiler:${Versions.hilt}"

    const val timber = "com.jakewharton.timber:timber:${Versions.timber}"
    const val leakCanary = "com.squareup.leakcanary:leakcanary-android:${Versions.leakCanary}"

    const val coreKtx = "androidx.core:core-ktx:${Versions.coreKtx}"
    const val annotations = "androidx.annotation:annotation:${Versions.annotations}"
    const val material = "com.google.android.material:material:${Versions.material}"

    val compose = listOf(
        "androidx.compose.ui:ui:${Versions.composeUi}",
        "androidx.compose.ui:ui-tooling:${Versions.composeUi}",
        "androidx.compose.foundation:foundation:${Versions.composeFoundation}",
        "androidx.compose.material:material:${Versions.composeMaterial}",
        "io.coil-kt:coil-compose:${Versions.coilCompose}",
        "androidx.navigation:navigation-compose:${Versions.navigationCompose}",
        "androidx.paging:paging-compose:${Versions.pagingCompose}"
    )

    val accompanist = listOf(
        "com.google.accompanist:accompanist-systemuicontroller:${Versions.accompanist}",
        "com.google.accompanist:accompanist-swiperefresh:${Versions.accompanist}"
    )

    const val recyclerView = "androidx.recyclerview:recyclerview:${Versions.recyclerView}"
    const val swipeRefreshLayout = "androidx.swiperefreshlayout:swiperefreshlayout:${Versions.swipeRefreshLayout}"

    const val retrofit2 = "com.squareup.retrofit2:retrofit:${Versions.retrofit2}"
    const val retrofit2ConverterGson = "com.squareup.retrofit2:converter-gson:${Versions.retrofit2}"
    const val okhttpLoggingInterceptor = "com.squareup.okhttp3:logging-interceptor:${Versions.okhttpLoggingInterceptor}"
    const val gson = "com.google.code.gson:gson:${Versions.gson}"

    const val dataStore = "androidx.datastore:datastore-preferences:${Versions.dataStore}"

    val room = listOf(
        "androidx.room:room-ktx:${Versions.room}",
        "androidx.room:room-paging:${Versions.room}"
    )
    const val roomCompiler = "androidx.room:room-compiler:${Versions.room}"

    object Versions {
        const val kotlin = "1.7.0"
        const val coroutines = "1.3.9"

        const val hilt = "2.44"

        const val timber = "4.6.1"
        const val leakCanary = "2.9.1"

        const val coreKtx = "1.8.0"
        const val annotations = "1.0.0"
        const val material = "1.2.0-alpha03"

        const val composeUi = "1.3.3"
        const val composeFoundation = "1.3.1"
        const val composeMaterial = "1.3.1"
        const val composeCompilerExtension = "1.3.2"

        const val navigationCompose = "2.5.1"
        const val pagingCompose = "1.0.0-alpha16"
        const val coilCompose = "2.1.0"

        const val accompanist = "0.26.0-alpha"

        const val recyclerView = "1.1.0"
        const val swipeRefreshLayout = "1.0.0"

        const val retrofit2 = "2.8.1"
        const val okhttpLoggingInterceptor = "4.2.1"
        const val gson = "2.8.6"

        const val dataStore = "1.0.0"

        const val room = "2.4.3"
    }
}

import org.gradle.api.artifacts.dsl.DependencyHandler

fun DependencyHandler.api(items: Collection<String>) = items.forEach { add("api", it) }

fun DependencyHandler.implementation(items: Collection<String>) = items.forEach { add("implementation", it) }

object Versions {
    const val kotlin = "1.7.10"
    const val coroutines = "1.3.9"

    const val timber = "4.6.1"
    const val leakCanary = "2.2"

    const val coreKtx = "1.8.0"
    const val annotations = "1.0.0"

    const val material = "1.2.0-alpha03"
    const val compose = "1.2.0"

    const val fragmentKtx = "1.5.1"
    const val navigationKtx = "2.5.1"

    const val recyclerView = "1.1.0"
    const val swipeRefreshLayout = "1.0.0"

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

    const val coreKtx = "androidx.core:core-ktx:${Versions.coreKtx}"
    const val annotations = "androidx.annotation:annotation:${Versions.annotations}"

    const val material = "com.google.android.material:material:${Versions.material}"
    val compose = listOf(
        "androidx.compose.ui:ui:${Versions.compose}",
        "androidx.compose.ui:ui-tooling:${Versions.compose}",
        "androidx.compose.foundation:foundation:${Versions.compose}",
        "androidx.compose.material:material:${Versions.compose}",
        "androidx.navigation:navigation-compose:${Versions.navigationKtx}"
    )

    const val fragmentKtx = "androidx.fragment:fragment-ktx:${Versions.fragmentKtx}"
    val navigationKtx = listOf(
        "androidx.navigation:navigation-fragment-ktx:${Versions.navigationKtx}",
        "androidx.navigation:navigation-ui-ktx:${Versions.navigationKtx}"
    )

    const val recyclerView = "androidx.recyclerview:recyclerview:${Versions.recyclerView}"
    const val swipeRefreshLayout = "androidx.swiperefreshlayout:swiperefreshlayout:${Versions.swipeRefreshLayout}"

    const val room = "androidx.room:room-ktx:${Versions.room}"
    const val roomCompiler = "androidx.room:room-compiler:${Versions.room}"

    const val retrofit2 = "com.squareup.retrofit2:retrofit:${Versions.retrofit2}"
    const val retrofit2ConverterGson = "com.squareup.retrofit2:converter-gson:${Versions.retrofit2}"
    const val okhttpLoggingInterceptor = "com.squareup.okhttp3:logging-interceptor:${Versions.okhttpLoggingInterceptor}"
    const val gson = "com.google.code.gson:gson:${Versions.gson}"
}
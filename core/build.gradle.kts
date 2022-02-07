plugins {
    id("com.android.library")
}

apply(from = "$rootDir/android.gradle")

android {
    viewBinding.isEnabled = true
}

dependencies {
    implementation(fileTree("dir" to "libs", "include" to listOf("*.jar")))

    api (project(":extensions:android"))
    api (project(":extensions:kotlin"))
    api (project(":extensions:lifecycle"))
    api (project(":extensions:retrofit"))
    api (project(":extensions:room"))
    api (project(":extensions:rx"))

    api (project(":database"))

    api (libraries.getValue("coroutines"))

    api (libraries.getValue("timber"))

    api (libraries.getValue("rxAndroid"))
    api (libraries.getValue("rxJava"))

    api (libraries.getValue("retrofit2"))
    api (libraries.getValue("roomRuntime"))

    api (libraries.getValue("coreKtx"))

    api (libraries.getValue("appCompat"))

    api (libraries.getValue("constraintLayout"))
    api (libraries.getValue("swipeRefreshLayout"))
    api (libraries.getValue("recyclerView"))

    api (libraries.getValue("navigationFragmentKtx"))
    api (libraries.getValue("navigationUiKtx"))

    api (libraries.getValue("lifecycleExtensions"))
    api (libraries.getValue("lifecycleCommonJava8"))
    api (libraries.getValue("lifecycleReactivestreams"))
    
    api (libraries.getValue("pagingRuntime"))
    api (libraries.getValue("pagingRxJava2"))
}

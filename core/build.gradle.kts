plugins {
    id("com.android.library")
}

apply(from = "$rootDir/android.gradle")

dependencies {
    implementation(fileTree("dir" to "libs", "include" to listOf("*.jar")))

    api (project(":api"))
    api (project(":database"))
    api (project(":legacy"))

    api (libraries.getValue("coroutines"))

    api (libraries.getValue("timber"))

    api (libraries.getValue("retrofit2"))
    api (libraries.getValue("roomRuntime"))

    api (libraries.getValue("coreKtx"))
    api (libraries.getValue("material"))

    api (libraries.getValue("appCompat"))

    api (libraries.getValue("navigationFragmentKtx"))
}

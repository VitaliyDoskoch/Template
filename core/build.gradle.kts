plugins {
    id("com.android.library")
    id("android-module")
    id("compose")
}

dependencies {
    api(project(":legacy"))

    implementation(project(":api"))
    implementation(project(":database"))

    api(Dependencies.timber)
    api(Dependencies.coreKtx)
    api(Dependencies.material)

    api(Dependencies.paging)

    implementation(Dependencies.gson)
    implementation(Dependencies.retrofit2)
    implementation(Dependencies.dataStore)
    implementation(Dependencies.room)
}

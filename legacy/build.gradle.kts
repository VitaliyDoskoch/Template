plugins {
    id("com.android.library")
    id("android-module")
}

android {
    namespace = "com.doskoch.legacy"
}

dependencies {
    implementation(Libraries.material)
    implementation(Libraries.coreKtx)
    implementation(Libraries.recyclerView)
    implementation(Libraries.swipeRefreshLayout)
}

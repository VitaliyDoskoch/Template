plugins {
    id("android-module")
    id("compose")
}

dependencies {
    implementation(project(":api"))
    implementation(project(":database"))
    implementation(project(":core"))

    implementation(Dependencies.timber)
    implementation(Dependencies.coreKtx)
//    implementation(Dependencies.material)
}
plugins {
    id("android-module")
    id("compose")
}

dependencies {
    implementation(project(":core"))

    implementation(Dependencies.timber)
    implementation(Dependencies.coreKtx)
    implementation(Dependencies.material)
}
plugins {
    id("com.android.application")
    id("android-module")
    id("compose")
}

android {
    buildTypes {
        debug {
            isMinifyEnabled = false
        }
        release {
            isMinifyEnabled = true
            isShrinkResources = true

            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
}

dependencies {
    implementation(project(":api"))
    implementation(project(":database"))
    implementation(project(":core"))

    implementation(project(":features:splash"))
    implementation(project(":features:authorization"))
    implementation(project(":features:anime"))

    debugImplementation(Dependencies.leakCanary)

    implementation(Dependencies.fragmentKtx)
    implementation(Dependencies.navigationKtx)

    compileOnly(Dependencies.dataStore)
}

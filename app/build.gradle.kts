plugins {
    id("com.android.application")
    id("android-module")
    id("compose")
}

android {
    namespace = "com.doskoch.template"

    buildTypes {
        debug {
            isMinifyEnabled = false
        }
        release {
            isMinifyEnabled = true
            isShrinkResources = true

            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
}

dependencies {
    implementation(project(":api"))
    implementation(project(":database"))

    implementation(project(":core:android"))
    implementation(project(":core:kotlin"))
    implementation(project(":core:data"))
    implementation(project(":core:domain"))

    implementation(project(":features:splash:presentation"))

    implementation(project(":features:auth:presentation"))

    implementation(project(":features:anime"))
    implementation(project(":features:anime:data"))
    implementation(project(":features:anime:presentation"))

    debugImplementation(Libraries.leakCanary)

    implementation(Libraries.material)
    implementation(Libraries.retrofit2)
    implementation(Libraries.room)
}

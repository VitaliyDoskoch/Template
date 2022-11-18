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

val installGitHook: Task by tasks.creating {
    println("Copying files from the 'rootDir/git-hooks' folder into the '.git/hooks' folder")
    copy { from("$rootDir/git-hooks").into("$rootDir/.git/hooks") }
}

tasks.getByPath(":app:preBuild").dependsOn(installGitHook)

dependencies {
    implementation(project(":api"))
    implementation(project(":database"))
    implementation(project(":core"))

    implementation(project(":features:splash"))
    implementation(project(":features:auth"))
    implementation(project(":features:anime"))

    debugImplementation(Libraries.leakCanary)

    implementation(Libraries.material)
    implementation(Libraries.room)
}

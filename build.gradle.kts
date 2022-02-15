buildscript {
    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        classpath("com.android.tools.build:gradle:7.1.1")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.10")
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.3.2")
//        classpath "de.mannodermaus.gradle.plugins:android-junit5:1.6.0.0"
    }
}

extra.set("kotlin_version", "1.6.10")

tasks {
    register("clean", Delete::class) {
        delete(rootProject.buildDir)
    }
}

apply(from = "$rootDir/libraries.gradle")
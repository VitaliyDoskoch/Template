buildscript {
    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        classpath("com.android.tools.build:gradle:7.1.1")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}")
    }
}

tasks {
    register("clean", Delete::class) { delete(rootProject.buildDir) }
}

apply(from = "$rootDir/libraries.gradle")
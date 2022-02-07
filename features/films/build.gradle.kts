plugins {
    id("com.android.library")
}

apply(from = "$rootDir/android.gradle")

android {
    viewBinding.isEnabled = true
}

dependencies {
    implementation(fileTree("dir" to "libs", "include" to listOf("*.jar")))

    api (project(":core"))
    api (project(":apis:the_movie_db"))

    api (libraries.getValue("glide"))
}

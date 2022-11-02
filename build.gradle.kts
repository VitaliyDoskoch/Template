buildscript {
    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        classpath(Plugins.android)
        classpath(Plugins.kotlin)
    }
}

tasks {
    register("clean", Delete::class) { delete(rootProject.buildDir) }
}
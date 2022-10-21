buildscript {
    repositories {
        google()
        mavenCentral()
    }

    val classpathProperties = java.util.Properties().apply {
        load(java.io.FileInputStream(File(rootDir, "classpath.properties")))
    }

    dependencies {
        classpathProperties.values.forEach { classpath(it) }
    }
}

tasks {
    register("clean", Delete::class) { delete(rootProject.buildDir) }
}
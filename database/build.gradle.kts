plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
}

apply(from = "$rootDir/android.gradle")

android {
    defaultConfig {
        javaCompileOptions {
            annotationProcessorOptions {
                arguments += mapOf(
                    "room.schemaLocation"  to "$projectDir/databaseSchemas".toString(),
                    "room.incremental"     to "true",
                    "room.expandProjection" to "true"
                )
            }
        }
    }
}

dependencies {
    implementation(fileTree("dir" to "libs", "include" to listOf("*.jar")))

    api (project(":extensions:room"))

    api (libraries.getValue("roomRuntime"))
    kapt (processors.getValue("roomCompiler"))

    androidTestImplementation (testLibraries.getValue("kotlinTestJunit"))
    androidTestImplementation (testLibraries.getValue("junitJupiterParams"))
    androidTestImplementation (testLibraries.getValue("testRunner"))
}

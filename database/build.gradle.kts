plugins {
    id("com.android.library")
    id("android-module")
}

android {
    namespace = "com.doskoch.template.database"

    defaultConfig {
        javaCompileOptions {
            annotationProcessorOptions {
                arguments += mapOf(
                    "room.schemaLocation" to "$projectDir/schemas",
                    "room.incremental" to "true",
                    "room.expandProjection" to "true"
                )
            }
        }
    }
}

dependencies {
    kapt(Libraries.roomCompiler)
    implementation(Libraries.room)
}

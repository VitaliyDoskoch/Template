import com.android.build.gradle.LibraryExtension
import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import org.gradle.kotlin.dsl.dependencies

@Suppress("UnstableApiUsage")
android {
    when(this) {
        is BaseAppModuleExtension -> {
            buildFeatures {
                compose = true
            }
            composeOptions {
                kotlinCompilerExtensionVersion = Libraries.Versions.compose
            }
        }
        is LibraryExtension -> {
            buildFeatures {
                compose = true
            }
            composeOptions {
                kotlinCompilerExtensionVersion = Libraries.Versions.compose
            }
        }
    }
}

dependencies {
    implementation(Libraries.compose)
    implementation(Libraries.accompanist)
}
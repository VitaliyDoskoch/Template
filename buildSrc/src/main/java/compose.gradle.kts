import com.android.build.gradle.LibraryExtension
import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import org.gradle.kotlin.dsl.dependencies

android {
    when(this) {
        is BaseAppModuleExtension -> {
            buildFeatures {
                compose = true
            }
            composeOptions {
                kotlinCompilerExtensionVersion = Dependencies.Versions.compose
            }
        }
        is LibraryExtension -> {
            buildFeatures {
                compose = true
            }
            composeOptions {
                kotlinCompilerExtensionVersion = Dependencies.Versions.compose
            }
        }
    }
}

dependencies {
    implementation(Dependencies.compose)
    implementation(Dependencies.accompanist)
}
import com.android.build.gradle.BaseExtension
import org.gradle.api.Project
import org.gradle.api.Action
import org.gradle.api.plugins.ExtensionAware

internal fun Project.android(configure: Action<BaseExtension>) {
    (this as ExtensionAware).extensions.configure("android", configure)
}
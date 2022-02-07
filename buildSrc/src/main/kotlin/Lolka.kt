import org.gradle.api.Project
import org.gradle.api.artifacts.Configuration
import org.gradle.internal.extensibility.DefaultExtraPropertiesExtension
import org.gradle.internal.service.scopes.Scopes
import org.gradle.kotlin.dsl.resolver.buildSrcSourceRootsFilePath

val Project.testLibraries: Map<String, String>
    get() {
        val extra = rootProject.properties["ext"] as DefaultExtraPropertiesExtension
        return extra.properties["testLibraries"] as Map<String, String>
    }

val Project.libraries: Map<String, String>
    get() {
        val extra = rootProject.properties["ext"] as DefaultExtraPropertiesExtension
        return extra.properties["libraries"] as Map<String, String>
    }

val Project.processors: Map<String, String>
    get() {
        val extra = rootProject.properties["ext"] as DefaultExtraPropertiesExtension
        return extra.properties["processors"] as Map<String, String>
    }
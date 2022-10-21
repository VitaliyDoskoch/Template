import org.gradle.api.artifacts.dsl.DependencyHandler

const val implementation = "implementation"
const val debugImplementation = "debugImplementation"
const val api = "api"

const val testImplementation = "testImplementation"
const val testRuntimeOnly = "testRuntimeOnly"

const val androidTestImplementation = "androidTestImplementation"
const val androidTestRuntimeOnly = "androidTestRuntimeOnly"
const val androidTestUtil = "androidTestUtil"

const val kapt = "kapt"
const val kaptTest = "kaptTest"
const val kaptAndroidTest = "kaptAndroidTest"

fun DependencyHandler.implementation(items: Collection<String>) = items.forEach { add(implementation, it) }

fun DependencyHandler.api(items: Collection<String>) = items.forEach { add(api, it) }

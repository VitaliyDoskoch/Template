plugins {
    id("feature-data")
}

android {
    namespace = "com.doskoch.template.core.data"
}

dependencies {
    implementation(project(":core:domain"))

    implementation(Libraries.dataStore)
}
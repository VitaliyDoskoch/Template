plugins {
    id("feature-presentation")
}

android {
    namespace = "com.doskoch.template.auth.presentation"
}

dependencies {
    implementation(project(":features:auth:domain"))
}
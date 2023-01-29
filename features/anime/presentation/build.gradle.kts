plugins {
    id("feature-presentation")
}

android {
    namespace = "com.doskoch.template.anime.presentation"
}

dependencies {
    implementation(project(":features:anime:domain"))
}
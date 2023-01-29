plugins {
    id("feature-data")
}

android {
    namespace = "com.doskoch.template.anime.data"
}

dependencies {
    implementation(project(":features:anime:domain"))
}
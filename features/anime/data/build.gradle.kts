plugins {
    id("feature-data")
}

android {
    namespace = "com.doskoch.template.anime.data"
}

dependencies {
    //TODO: move to feature-data script
    implementation(project(":api"))
    implementation(project(":database"))
}
plugins {
    id("feature-presentation")
}

android {
    namespace = "com.doskoch.template.anime"
}

dependencies {
    //TODO: remove
    implementation(project(":api"))
    implementation(project(":database"))


}
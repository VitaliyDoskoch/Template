plugins {
    id("feature-data")
}

android {
    namespace = "com.doskoch.template.anime.data"
}

dependencies {
    implementation(project(":features:anime:domain"))
    implementation("androidx.paging:paging-common-ktx:3.1.1")
}
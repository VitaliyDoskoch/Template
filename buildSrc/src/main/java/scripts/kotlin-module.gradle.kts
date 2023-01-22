plugins {
    id("kotlin")
    id("ktlint")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

dependencies {
    implementation(fileTree("dir" to "libs", "include" to listOf("*.jar")))

    implementation(Libraries.kotlin)
    implementation(Libraries.coroutines)

    implementation(Libraries.javaxInject)
}
rootProject.name = "Movies"

pluginManagement {
    repositories {
        google()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
}

include(":app")
include(":core")
include(":database")

include(":extensions:android")
include(":extensions:android_test")
include(":extensions:kotlin")
include(":extensions:lifecycle")
include(":extensions:lifecycle_test")
include(":extensions:retrofit")
include(":extensions:room")
include(":extensions:rx")

include(":apis:the_movie_db")

include(":features:splash")
include(":features:films")

include(":legacy")
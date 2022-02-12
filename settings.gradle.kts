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

include(":extensions:retrofit")
include(":extensions:room")
include(":extensions:rx")

include(":apis:the_movie_db")

include(":features:splash")

include(":legacy")
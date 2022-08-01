rootProject.name = "Template"

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
}

include(":app")
include(":core")
include(":api")
include(":database")
include(":legacy")

include(":features:splash")
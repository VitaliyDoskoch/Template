rootProject.name = "Template"

@Suppress("UnstableApiUsage")
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
include(":features:anime")
include(":features:auth:data")
include(":features:auth:domain")
include(":features:auth:presentation")

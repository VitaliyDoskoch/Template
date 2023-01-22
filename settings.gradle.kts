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
include(":core:domain")

include(":api")
include(":database")
include(":legacy")

include(":features:splash:presentation")
include(":features:auth:domain")
include(":features:auth:presentation")
include(":features:anime")
include(":core:data")

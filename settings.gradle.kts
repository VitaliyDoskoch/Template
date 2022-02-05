rootProject.name = "Movies"

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
include(":features:main")
include(":features:films")
include(":features:films_all")
include(":features:films_favourite")
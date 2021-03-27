package com.doskoch.movies.features.films

import androidx.paging.PagedList
import com.doskoch.apis.the_movie_db.config.LIST_PAGE_SIZE

const val REQUESTED_POSTER_SIZE = "w185"
const val MODULE_IMAGES_PATH = "/main_shared/images/"
const val POSTER_FILE_NAME = "Poster.jpg"

val PAGED_LIST_CONFIG = PagedList.Config.Builder()
    .setEnablePlaceholders(true)
    .setInitialLoadSizeHint(LIST_PAGE_SIZE)
    .setPageSize(LIST_PAGE_SIZE)
    .setPrefetchDistance(LIST_PAGE_SIZE)
    .build()
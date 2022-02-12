package com.doskoch.api.the_movie_db.components.service

import com.doskoch.api.the_movie_db.components.handlerProviders.ErrorHandlerProvider
import com.doskoch.api.the_movie_db.components.handlerProviders.ResponseHandlerProvider

class ServiceConnectorModule<S>(
    val service: S,
    val responseHandlerProvider: ResponseHandlerProvider? = null,
    val errorHandlerProvider: ErrorHandlerProvider? = null
)
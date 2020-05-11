package com.extensions.retrofit.components.service

import com.extensions.retrofit.components.handlerProviders.ErrorHandlerProvider
import com.extensions.retrofit.components.handlerProviders.ResponseHandlerProvider

class ServiceConnectorModule<S>(
    val service: S,
    val responseHandlerProvider: ResponseHandlerProvider? = null,
    val errorHandlerProvider: ErrorHandlerProvider? = null
)
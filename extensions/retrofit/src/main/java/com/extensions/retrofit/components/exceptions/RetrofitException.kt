package com.extensions.retrofit.components.exceptions

import retrofit2.Call

open class RetrofitException(val call: Call<*>, cause: Throwable) : RuntimeException(cause)
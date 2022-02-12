package com.doskoch.apis.the_movie_db.components.exceptions

import retrofit2.Call

open class RetrofitException(val call: Call<*>, cause: Throwable) : RuntimeException(cause)
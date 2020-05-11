package com.doskoch.movies.features.films.functions

import android.annotation.SuppressLint
import com.doskoch.apis.the_movie_db.config.DATE_FORMAT
import java.text.SimpleDateFormat

@SuppressLint("SimpleDateFormat")
fun convertDate(date: String): Long {
    return SimpleDateFormat(DATE_FORMAT).parse(date).time
}

@SuppressLint("SimpleDateFormat")
fun convertDate(date: Long): String {
    return SimpleDateFormat(DATE_FORMAT).format(date)
}
package com.doskoch.movies.features.films.functions

import android.content.Context
import android.content.Intent
import androidx.core.content.FileProvider
import com.bumptech.glide.Glide
import com.doskoch.apis.the_movie_db.config.BASE_IMAGE_URL
import com.doskoch.movies.database.modules.films.view.Film
import com.doskoch.movies.features.films.R
import com.doskoch.movies.features.films.MODULE_IMAGES_PATH
import com.doskoch.movies.features.films.POSTER_FILE_NAME
import com.doskoch.movies.features.films.REQUESTED_POSTER_SIZE
import io.reactivex.Completable
import java.io.File

fun fullPosterUrl(posterPath: String?): String {
    return "%s%s/%s".format(
        BASE_IMAGE_URL,
        REQUESTED_POSTER_SIZE,
        posterPath
    )
}

fun Context.posterPath(): String {
    return "%s%s%s".format(
        cacheDir.path,
        MODULE_IMAGES_PATH,
        POSTER_FILE_NAME
    )
}

fun Context.preparePosterForSharing(posterPath: String?): Completable {
    return if (posterPath != null) {
        Completable.fromCallable {
            getCachedPosterFile(posterPath).copyTo(File(posterPath()), overwrite = true)
        }
    } else {
        Completable.fromCallable {
            File(posterPath()).delete()
        }
    }
}

private fun Context.getCachedPosterFile(posterPath: String): File {
    return Glide.with(this)
        .asFile()
        .load(fullPosterUrl(posterPath))
        .submit()
        .get()
}

fun Context.shareFilm(item: Film) {
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "*/*"

        putExtra(Intent.EXTRA_TEXT, getSharedText(item.title, item.overview))

        val posterFile = File(posterPath())
        if (posterFile.exists()) {
            val uri = FileProvider.getUriForFile(this@shareFilm, packageName, posterFile)
            putExtra(Intent.EXTRA_STREAM, uri)
        }
    }

    startActivity(Intent.createChooser(intent, getString(R.string.share)))
}

private fun getSharedText(title: String, overview: String): String {
    return "%s%s%s%s".format(
        title,
        if (title.isBlank() || overview.isBlank()) "" else "\n",
        if (title.isBlank() || overview.isBlank()) "" else "\n",
        overview
    )
}
package com.doskoch.template.anime.di

import com.doskoch.template.anime.AnimeNestedNavigator
import com.doskoch.template.core.error.GlobalErrorHandler

interface AnimeFeature {
    val navigator: AnimeFeatureNavigator
    val nestedNavigator: AnimeNestedNavigator
    val globalErrorHandler: GlobalErrorHandler
    val repository: AnimeFeatureRepository
}

interface AnimeFeatureNavigator {

}

interface AnimeFeatureRepository {

}
package com.doskoch.template.anime

import com.doskoch.legacy.kotlin.DestroyableLazy
import com.doskoch.template.core.error.GlobalErrorHandler

interface AnimeFeature {
    val navigator: AnimeFeatureNavigator
    val nestedNavigator: AnimeNestedNavigator
    val globalErrorHandler: GlobalErrorHandler
}

interface AnimeFeatureNavigator {

}

object AnimeFeatureInjector {
    var provider: DestroyableLazy<AnimeFeature>? = null
}

internal val Injector: AnimeFeature
    get() = AnimeFeatureInjector.provider!!.value

object Module {



}
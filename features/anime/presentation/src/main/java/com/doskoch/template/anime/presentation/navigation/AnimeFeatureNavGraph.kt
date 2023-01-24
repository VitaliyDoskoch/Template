package com.doskoch.template.anime.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import com.doskoch.template.anime.presentation.di.AnimeFeatureInjector
import com.doskoch.template.anime.presentation.screens.details.AnimeDetailsScreen
import com.doskoch.template.anime.presentation.screens.favorite.FavoriteAnimeScreen
import com.doskoch.template.anime.presentation.screens.top.TopAnimeScreen
import com.doskoch.template.core.android.components.navigation.CoreNavGraph
import com.doskoch.template.core.android.components.navigation.NavigationNode
import com.doskoch.template.core.android.components.navigation.NotNullStringType
import com.doskoch.template.core.android.components.navigation.composable
import com.doskoch.template.core.android.components.navigation.typedArgument

@Composable
fun AnimeFeatureNavGraph() {
    CoreNavGraph(navigator = com.doskoch.template.anime.presentation.di.AnimeFeatureInjector.navigator()) {
        Node.Top.composable(this) {
            TopAnimeScreen()
        }
        Node.Favorite.composable(this) {
            FavoriteAnimeScreen()
        }
        Node.Details.composable(this) {
            val args = requireNotNull(it.arguments)

            AnimeDetailsScreen(
                animeId = animeId.valueFrom(args),
                title = title.valueFrom(args)
            )
        }
    }
}

internal sealed class Node(name: String) : NavigationNode(name) {
    object Top : Node("top")
    object Favorite : Node("favorite")

    object Details : Node("details") {
        val animeId = typedArgument("animeId", NavType.IntType)
        val title = typedArgument("title", NavType.NotNullStringType)

        fun buildRoute(animeId: Int, title: String) = buildRoute(
            Details.animeId setValue animeId,
            Details.title setValue title
        )
    }
}

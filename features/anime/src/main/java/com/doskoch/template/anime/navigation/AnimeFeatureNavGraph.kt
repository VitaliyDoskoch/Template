package com.doskoch.template.anime.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import com.doskoch.template.anime.di.Injector
import com.doskoch.template.anime.screens.details.AnimeDetailsScreen
import com.doskoch.template.anime.screens.favorite.FavoriteAnimeScreen
import com.doskoch.template.anime.screens.top.TopAnimeScreen
import com.doskoch.template.core.components.navigation.CoreNavGraph
import com.doskoch.template.core.components.navigation.NavigationNode
import com.doskoch.template.core.components.navigation.NotNullStringType
import com.doskoch.template.core.components.navigation.composable
import com.doskoch.template.core.components.navigation.typedArgument

@Composable
fun AnimeFeatureNavGraph() {
    CoreNavGraph(navigator = Injector.navigator) {
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
            this.animeId setValue animeId,
            this.title setValue title
        )
    }
}

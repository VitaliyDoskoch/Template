package com.doskoch.template.anime.di

import androidx.paging.ExperimentalPagingApi
import com.doskoch.template.anime.screens.details.AnimeDetailsViewModel
import com.doskoch.template.anime.screens.favorite.FavoriteAnimeViewModel
import com.doskoch.template.anime.screens.top.TopAnimeViewModel
import com.doskoch.template.api.jikan.services.top.responses.GetTopAnimeResponse
import com.doskoch.template.core.components.kotlin.DestroyableLazy
import com.doskoch.template.core.components.paging.SimpleInMemoryStorage

internal object _Module {

    private val topAnimeStorage = DestroyableLazy(initialize = { SimpleInMemoryStorage<Int, GetTopAnimeResponse.Data>() })

    @ExperimentalPagingApi
    fun topAnimeViewModel(): TopAnimeViewModel {
        TODO()
//        TopAnimeViewModel(
//            navigator = AnimeFeatureInjector.navigator(),
//            pagerFactory = { remoteAnimeType ->
//                Pager(
//                    config = PAGING_CONFIG,
//                    remoteMediator = topRemoteMediator(remoteAnimeType),
//                    pagingSourceFactory = topAnimeStorage.value::SimplePagingSource
//                )
//            },
//            clearAnimeStorageUseCase = ClearAnimeStorageUseCase(storage = topAnimeStorage.value),
//            getFavoriteAnimeIdsUseCase = GetFavoriteAnimeIdsUseCase(dbAnimeDao = Injector.dbAnimeDao),
//            saveAnimeToFavoriteUseCase = SaveAnimeToFavoriteUseCase(storage = topAnimeStorage.value, dbAnimeDao = Injector.dbAnimeDao),
//            deleteAnimeFromFavoriteUseCase = DeleteAnimeFromFavoriteUseCase(dbAnimeDao = Injector.dbAnimeDao),
//            logoutUseCase = CoreModule.logoutUseCase(),
//            globalErrorHandler = Injector.globalErrorHandler
//        ).also {
//            it.addCloseable { topAnimeStorage.destroyInstance() }
//        }
    }

//    private fun topRemoteMediator(remoteAnimeType: RemoteAnimeType) = TopAnimeRemoteMediator(
//        remoteAnimeType = remoteAnimeType,
//        getLastPagingKeyUseCase = GetLastPagingKeyUseCase(storage = topAnimeStorage.value),
//        loadAnimeUseCase = LoadAnimeUseCase(service = Injector.topService),
//        storeAnimeUseCase = StoreAnimeUseCase(storage = topAnimeStorage.value),
//        globalErrorHandler = Injector.globalErrorHandler
//    )

    fun favoriteAnimeViewModel(): FavoriteAnimeViewModel {
        TODO()
//        FavoriteAnimeViewModel(
//            navigator = Injector.navigator,
//            pager = Pager(
//                config = PAGING_CONFIG,
//                pagingSourceFactory = Injector.dbAnimeDao::pagingSource
//            ),
//            deleteAnimeFromFavoriteUseCase = DeleteAnimeFromFavoriteUseCase(dbAnimeDao = Injector.dbAnimeDao),
//            globalErrorHandler = Injector.globalErrorHandler
//        )
    }

    fun animeDetailsViewModel(animeId: Int, title: String): AnimeDetailsViewModel {
        TODO()
//        AnimeDetailsViewModel(
//            animeId = animeId,
//            title = title,
//            navigator = Injector.navigator,
//            globalErrorHandler = Injector.globalErrorHandler,
//            getIsFavoriteAnimeUseCase = GetIsFavoriteAnimeUseCase(dbAnimeDao = Injector.dbAnimeDao),
//            loadAnimeDetailsUseCase = LoadAnimeDetailsUseCase(Injector.animeService),
//            saveAnimeToFavoriteUseCase = SaveAnimeToFavoriteUseCase(storage = topAnimeStorage.value, dbAnimeDao = Injector.dbAnimeDao),
//            deleteAnimeFromFavoriteUseCase = DeleteAnimeFromFavoriteUseCase(dbAnimeDao = Injector.dbAnimeDao)
//        )
    }
}

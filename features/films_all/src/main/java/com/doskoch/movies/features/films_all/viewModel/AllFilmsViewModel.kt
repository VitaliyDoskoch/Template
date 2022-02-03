package com.doskoch.movies.features.films_all.viewModel

import android.content.Context
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import com.doskoch.movies.core.components.rx.RxViewModel
import com.doskoch.movies.core.components.ui.base.recyclerView.paging.boundaryCallback.BoundaryCallbackHandler
import com.doskoch.movies.core.components.ui.base.recyclerView.paging.boundaryCallback.BoundaryCallbackResult
import com.doskoch.movies.core.functions.simpleRxAction
import com.doskoch.movies.core.functions.waitForNetwork
import com.doskoch.movies.database.modules.films.view.Film
import com.doskoch.movies.features.films_all.dataSource.AllFilmsDataSource
import com.doskoch.movies.features.films_all.repository.AllFilmsApiToDbConverter
import com.doskoch.movies.features.films_all.repository.api.AllFilmsApiRepository
import com.doskoch.movies.features.films_all.repository.db.AllFilmsDbRepository
import com.extensions.lifecycle.components.State
import com.extensions.retrofit.components.exceptions.NoInternetException
import com.extensions.rx.components.schedulers.ioScheduler
import com.extensions.rx.components.schedulers.mainThread
import com.extensions.rx.functions.createExecutor
import com.extensions.rx.functions.recreateOnEvent
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.disposables.Disposable
import timber.log.Timber

class AllFilmsViewModel(private val dependencies: Dependencies) : RxViewModel() {

    data class Dependencies(
        val context: Context,
        val dbRepository: AllFilmsDbRepository,
        val apiRepository: AllFilmsApiRepository,
        val converter: AllFilmsApiToDbConverter,
        val pagedListConfig: PagedList.Config,
        val minPageKey: Int,
        val createDataSource: (totalCount: Int, onLoadRangeError: (throwable: Throwable) -> Unit) -> AllFilmsDataSource
    )

    private val pagedListData by lazy { MutableLiveData<State<PagedList<Film>>>() }
    private val boundaryCallbackData by lazy { MutableLiveData<BoundaryCallbackResult<Film>>() }

    private val nextPageKey: Int
        get() = totalCount / dependencies.pagedListConfig.pageSize + dependencies.minPageKey

    private var totalCount: Int = 0
    private var lastLoadedPageKey: Int = -1

    fun pagedListData(): LiveData<State<PagedList<Film>>> {
        return pagedListData.also {
            addObserver(it, this::observePagedList)
        }
    }

    private fun observePagedList(): Disposable {
        return dependencies.dbRepository.observeChanges()
            .flatMap {
                Completable.fromCallable {
                    pagedListData.value?.data?.dataSource?.invalidate()
                }
                    .andThen(dependencies.dbRepository.count())
                    .doOnSuccess { totalCount = it }
                    .map { totalCount ->
                        buildPagedList(totalCount, pagedListData.value?.data?.lastKey as? Int ?: 0)
                    }
                    .toFlowable()
                    .materialize()
            }
            .subscribeOn(ioScheduler)
            .observeOn(mainThread)
            .doOnSubscribe {
                pagedListData.value = State.Loading()
            }
            .subscribe({
                when {
                    it.isOnNext -> pagedListData.value = State.Success(it.value!!)
                    it.isOnError -> it.error!!.let { error ->
                        Timber.e(error)
                        pagedListData.value = State.Failure(error)
                    }
                }
            }, {
                Timber.e(it)
                pagedListData.value = State.Failure(it)
            })
            .also { disposeOnCleared(it) }
    }

    private fun buildPagedList(totalCount: Int, initialKey: Int): PagedList<Film> {
        return PagedList.Builder(
            dependencies.createDataSource(totalCount, this::onLoadRangeError),
            dependencies.pagedListConfig
        )
            .setInitialKey(initialKey)
            .setBoundaryCallback(BoundaryCallbackHandler(boundaryCallbackData))
            .setFetchExecutor(ioScheduler.createExecutor())
            .setNotifyExecutor(mainThread.createExecutor())
            .build()
    }

    @WorkerThread
    private fun onLoadRangeError(throwable: Throwable) {
        mainThread.scheduleDirect {
            Timber.e(throwable)
            pagedListData.value = State.Failure(throwable)
        }
    }

    fun boundaryCallbackData(): LiveData<BoundaryCallbackResult<Film>> {
        return boundaryCallbackData
    }

    fun loadNextPage(): LiveData<State<Int>> {
        return MutableLiveData<State<Int>>().also { result ->
            val nextPageKey = this.nextPageKey

            if (lastLoadedPageKey == nextPageKey) {
                result.value = State.Success(0)
            } else {
                loadItems(result, nextPageKey)
            }
        }
    }

    private fun loadItems(result: MutableLiveData<State<Int>>, pageKey: Int) {
        recreateOnEvent { trigger ->
            simpleRxAction(result, ioScheduler) {
                dependencies.apiRepository.load(pageKey)
                    .doOnError {
                        if (it.cause is NoInternetException) {
                            waitForNetworkAsync(trigger)
                        }
                    }
                    .map { dependencies.converter.toDbFilms(it.results) }
                    .toFlowable()
                    .flatMap {
                        dependencies.dbRepository.save(it)
                            .doOnComplete { lastLoadedPageKey = pageKey }
                            .andThen(Flowable.just(it.size))
                    }
            }
        }
    }

    private fun waitForNetworkAsync(onAvailable: () -> Unit) {
        dependencies.context.waitForNetwork()
            .observeOn(mainThread)
            .subscribe(onAvailable, { Timber.e(it) })
            .also { disposeOnCleared(it) }
    }

    fun refresh(): LiveData<State<Any>> {
        return MutableLiveData<State<Any>>().also { result ->
            simpleRxAction(result, ioScheduler) {
                dependencies.apiRepository.load(dependencies.minPageKey)
                    .map { dependencies.converter.toDbFilms(it.results) }
                    .flatMapCompletable { dependencies.dbRepository.replaceAll(it) }
                    .andThen(Flowable.just(Any()))
            }
        }
    }

    fun switchFavourite(item: Film): LiveData<State<Any>> {
        return MutableLiveData<State<Any>>().also { result ->
            simpleRxAction(result, ioScheduler) {
                dependencies.dbRepository.switchFavourite(item)
                    .andThen(Flowable.just(Any()))
            }
        }
    }
}
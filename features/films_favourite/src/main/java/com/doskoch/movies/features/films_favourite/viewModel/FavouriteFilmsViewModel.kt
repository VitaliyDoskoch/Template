package com.doskoch.movies.features.films_favourite.viewModel

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import com.doskoch.movies.core.components.rx.RxViewModel
import com.doskoch.movies.core.functions.simpleRxAction
import com.doskoch.movies.database.modules.films.view.Film
import com.doskoch.movies.features.films_favourite.dataSource.FavouriteFilmsDataSource
import com.doskoch.movies.features.films_favourite.repository.db.FavouriteFilmsDbRepository
import com.extensions.lifecycle.components.State
import com.extensions.rx.components.schedulers.ioScheduler
import com.extensions.rx.components.schedulers.mainThread
import com.extensions.rx.functions.createExecutor
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.disposables.Disposable
import timber.log.Timber

class FavouriteFilmsViewModel(private val dependencies: Dependencies) : RxViewModel() {

    data class Dependencies(
        val dbRepository: FavouriteFilmsDbRepository,
        val pagedListConfig: PagedList.Config,
        val createDataSource: (totalCount: Int, onLoadRangeError: (throwable: Throwable) -> Unit) -> FavouriteFilmsDataSource
    )

    private val pagedListData by lazy { MutableLiveData<State<PagedList<Film>>>() }

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

    fun delete(item: Film): LiveData<State<Any>> {
        return MutableLiveData<State<Any>>().also { result ->
            simpleRxAction(result, ioScheduler) {
                dependencies.dbRepository.delete(item)
                    .andThen(Flowable.just(Any()))
            }
        }
    }
}
package com.doskoch.movies.features.films_all.viewModel

import androidx.arch.core.executor.ArchTaskExecutor
import androidx.paging.PagedList
import com.doskoch.movies.core.functions.waitForNetwork
import com.doskoch.movies.database.modules.films.entity.DbFilm
import com.doskoch.movies.database.modules.films.view.Film
import com.doskoch.movies.features.films.config.PAGED_LIST_CONFIG
import com.doskoch.movies.features.films_all.dataSource.AllFilmsDataSource
import com.extensions.lifecycle.components.State
import com.extensions.lifecycle_test.components.StateTestHelper
import com.extensions.lifecycle_test.functions.createTaskExecutor
import com.extensions.retrofit.components.NO_INTERNET
import com.extensions.retrofit.components.exceptions.NetworkException
import com.extensions.retrofit.components.exceptions.NoInternetException
import com.extensions.rx.components.schedulers.trampolineScheduler
import com.extensions.rx.functions.initSchedulersHandler
import com.extensions.rx.functions.resetSchedulersHandler
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.invoke
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.slot
import io.mockk.spyk
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.processors.PublishProcessor
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertSame

class AllFilmsViewModelTest {

    val module = spyk(
        AllFilmsViewModelModule(
            context = mockk(),
            dbRepository = mockk(),
            apiRepository = mockk(),
            converter = mockk(),
            pagedListConfig = PAGED_LIST_CONFIG,
            minPageKey = 0
        )
    )

    lateinit var viewModel: AllFilmsViewModel

    @BeforeEach
    fun beforeEach() {
        ArchTaskExecutor.getInstance().setDelegate(createTaskExecutor())
        initSchedulersHandler(trampolineScheduler)

        viewModel = AllFilmsViewModel(module)
    }

    @AfterEach
    fun afterEach() {
        ArchTaskExecutor.getInstance().setDelegate(null)
        resetSchedulersHandler()
        clearAllMocks()
    }

    @Nested
    @DisplayName("pagedListData() works as expected")
    inner class PagedListData {

        @Test
        @DisplayName("An observer is notified with State.Loading")
        fun loadingState() {
            every { module.dbRepository.observeChanges() } returns Flowable.never()

            StateTestHelper { viewModel.pagedListData() }
                .capture<State.Loading<PagedList<Film>>> {
                    it.assertPureState()
                    assertNull(it.captured.data)
                }
        }

        @Test
        @DisplayName("An observer is notified with State.Success")
        fun successState() {
            val totalCount = 0
            val dataSource = mockk<AllFilmsDataSource>(relaxed = true)
            every { module.dbRepository.observeChanges() } returns Flowable.just(mockk())
            every { module.dbRepository.count() } returns Single.just(totalCount)
            every { module.createDataSource(totalCount, any()) } returns dataSource

            StateTestHelper { viewModel.pagedListData() }
                .capture<State.Success<PagedList<Film>>> {
                    it.assertPureState()
                    assertSame(dataSource, it.captured.data.dataSource)
                    assertSame(module.pagedListConfig, it.captured.data.config)
                }
        }

        @Nested
        @DisplayName("An observer is notified with State.Failure")
        inner class FailureState {

            @Test
            @DisplayName("Error in dbRepository::observeChanges")
            fun errorInObserveChanges() {
                val exception = Exception("test")
                every { module.dbRepository.observeChanges() } returns Flowable.error(exception)

                verifyFailure(exception)
            }

            @Test
            @DisplayName("Error in dbRepository::count")
            fun errorInCount() {
                val exception = Exception("test")
                every { module.dbRepository.observeChanges() } returns Flowable.just(mockk())
                every { module.dbRepository.count() } returns Single.error(exception)

                verifyFailure(exception)
            }

            @Test
            @DisplayName("Error in module::createDataSource")
            fun errorInCreateDataSource() {
                val exception = Exception("test")
                every { module.dbRepository.observeChanges() } returns Flowable.just(mockk())
                every { module.dbRepository.count() } returns Single.just(0)
                every { module.createDataSource(any(), any()) } throws exception

                verifyFailure(exception)
            }

            @Test
            @DisplayName("Error in dataSource::onLoadRangeError")
            fun onLoadRangeError() {
                val exception = Exception("test")
                val handler = slot<(Throwable) -> Unit>()
                every { module.dbRepository.observeChanges() } returns Flowable.just(mockk())
                every { module.dbRepository.count() } returns Single.just(0)
                every { module.createDataSource(any(), capture(handler)) } returns
                    mockk(relaxed = true)

                val liveData by lazy { viewModel.pagedListData() }

                StateTestHelper { liveData }
                    .capture<State.Success<PagedList<Film>>>()

                StateTestHelper { liveData.also { handler.invoke(exception) } }
                    .capture<State.Failure<PagedList<Film>>> {
                        it.assertPureState()
                        assertNull(it.captured.data)
                        assertSame(exception, it.captured.throwable)
                    }
            }

            private fun verifyFailure(exception: Exception) {
                StateTestHelper { viewModel.pagedListData() }
                    .capture<State.Failure<PagedList<Film>>> {
                        it.assertPureState()
                        assertNull(it.captured.data)
                        assertSame(exception, it.captured.throwable)
                    }
            }
        }
    }

    @Nested
    @DisplayName("loadNextPage() works as expected")
    inner class LoadNextPage {

        @Test
        @DisplayName("An observer is notified with State.Loading")
        fun loadingState() {
            every { module.apiRepository.load(any()) } returns Single.never()

            StateTestHelper { viewModel.loadNextPage() }
                .capture<State.Loading<Int>> {
                    it.assertPureState()
                    assertNull(it.captured.data)
                }
        }

        @Test
        @DisplayName("An observer is notified with State.Success")
        fun successState() {
            val items = listOf<DbFilm>(mockk(relaxed = true))
            every { module.apiRepository.load(any()) } returns Single.just(mockk(relaxed = true))
            every { module.converter.toDbFilms(any()) } returns items
            every { module.dbRepository.save(any()) } returns Completable.complete()

            StateTestHelper { viewModel.loadNextPage() }
                .capture<State.Success<Int>> {
                    it.assertPureState()
                    assertEquals(items.size, it.captured.data)
                }
        }

        @Test
        @DisplayName(
            """
            An observer is notified with State.Failure if there is no internet connection, 
            but retries action when connection gets restored
            """
        )
        fun retryWithNetwork() {
            mockkStatic("com.doskoch.movies.core.functions.NetworkKt")

            val exception = NetworkException(
                "testUrl",
                NetworkException.Type.NETWORK,
                NO_INTERNET,
                null,
                NoInternetException()
            )
            val processor = PublishProcessor.create<Any>()
            every { module.apiRepository.load(any()) } returns Single.error(exception)
            every { module.context.waitForNetwork() } returns processor.ignoreElements()

            val liveData by lazy { viewModel.loadNextPage() }

            StateTestHelper { liveData }
                .capture<State.Failure<Int>>()

            every { module.apiRepository.load(any()) } returns Single.never()
            processor.onComplete()

            StateTestHelper { liveData }
                .capture<State.Loading<Int>>()
        }

        @Nested
        @DisplayName("An observer is notified with State.Failure")
        inner class FailureState {

            @Test
            @DisplayName("Error in apiRepository::load")
            fun errorInLoad() {
                val exception = Exception("test")
                every { module.apiRepository.load(any()) } returns Single.error(exception)

                verifyError(exception)
            }

            @Test
            @DisplayName("Error in converter::toDbFilms")
            fun errorInToDbFilms() {
                val exception = Exception("test")
                every { module.apiRepository.load(any()) } returns Single.just(mockk(relaxed = true))
                every { module.converter.toDbFilms(any()) } throws exception

                verifyError(exception)
            }

            @Test
            @DisplayName("Error in dbRepository::save")
            fun errorInSave() {
                val exception = Exception("test")
                every { module.apiRepository.load(any()) } returns Single.just(mockk(relaxed = true))
                every { module.converter.toDbFilms(any()) } returns emptyList()
                every { module.dbRepository.save(any()) } returns Completable.error(exception)

                verifyError(exception)
            }

            private fun verifyError(exception: Exception) {
                StateTestHelper { viewModel.loadNextPage() }
                    .capture<State.Failure<Int>> {
                        it.assertPureState()
                        assertNull(it.captured.data)
                        assertSame(exception, it.captured.throwable)
                    }
            }
        }
    }

    @Nested
    @DisplayName("refresh() works as expected")
    inner class Refresh {

        val minPageKey = module.minPageKey

        @Test
        @DisplayName("An observer is notified with State.Loading")
        fun loadingState() {
            every { module.apiRepository.load(minPageKey) } returns Single.never()

            StateTestHelper { viewModel.refresh() }
                .capture<State.Loading<Any>> {
                    it.assertPureState()
                    assertNull(it.captured.data)
                }
        }

        @Test
        @DisplayName("An observer is notified with State.Success")
        fun successState() {
            every { module.apiRepository.load(minPageKey) } returns
                Single.just(mockk(relaxed = true))
            every { module.converter.toDbFilms(any()) } returns emptyList()
            every { module.dbRepository.replaceAll(any()) } returns Completable.complete()

            StateTestHelper { viewModel.refresh() }
                .capture<State.Success<Any>> {
                    it.assertPureState()
                    assertNotNull(it.captured.data)
                }
        }

        @Nested
        @DisplayName("An observer is notified with State.Failure")
        inner class FailureState {

            val exception = Exception("test")

            @Test
            @DisplayName("Error in apiRepository::load")
            fun errorInLoad() {
                every { module.apiRepository.load(minPageKey) } returns Single.error(exception)

                verifyError(exception)
            }

            @Test
            @DisplayName("Error in converter::toDbFilms")
            fun errorInToDbFilms() {
                every { module.apiRepository.load(minPageKey) } returns Single.just(mockk(relaxed = true))
                every { module.converter.toDbFilms(any()) } throws exception

                verifyError(exception)
            }

            @Test
            @DisplayName("Error in dbRepository::replaceAll")
            fun errorInSaving() {
                every { module.apiRepository.load(minPageKey) } returns Single.just(mockk(relaxed = true))
                every { module.converter.toDbFilms(any()) } returns emptyList()
                every { module.dbRepository.replaceAll(any()) } returns Completable.error(exception)

                verifyError(exception)
            }

            private fun verifyError(exception: Exception) {
                StateTestHelper { viewModel.refresh() }
                    .capture<State.Failure<Any>> {
                        it.assertPureState()
                        assertNull(it.captured.data)
                        assertSame(exception, it.captured.throwable)
                    }
            }
        }
    }

    @Nested
    @DisplayName("switchFavourite(Film) works as expected")
    inner class SwitchFavourite {

        @Test
        @DisplayName("An observer is notified with State.Loading")
        fun loadingState() {
            every { module.dbRepository.switchFavourite(any()) } returns Completable.never()

            StateTestHelper { viewModel.switchFavourite(mockk()) }
                .capture<State.Loading<Any>> {
                    it.assertPureState()
                    assertNull(it.captured.data)
                }
        }

        @Test
        @DisplayName("An observer is notified with State.Success")
        fun successState() {
            every { module.dbRepository.switchFavourite(any()) } returns Completable.complete()

            StateTestHelper { viewModel.switchFavourite(mockk()) }
                .capture<State.Success<Any>> {
                    it.assertPureState()
                    assertNotNull(it.captured.data)
                }
        }

        @Test
        @DisplayName("An observer is notified with State.Failure")
        fun failureState() {
            val exception = Exception("test")
            every { module.dbRepository.switchFavourite(any()) } returns Completable.error(exception)

            StateTestHelper { viewModel.switchFavourite(mockk()) }
                .capture<State.Failure<Any>> {
                    it.assertPureState()
                    assertNull(it.captured.data)
                    assertSame(exception, it.captured.throwable)
                }
        }
    }
}
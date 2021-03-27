package com.doskoch.movies.features.films_favourite.viewModel

import androidx.arch.core.executor.ArchTaskExecutor
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.doskoch.movies.database.modules.films.entity.DbFavouriteFilm
import com.doskoch.movies.database.modules.films.view.Film
import com.doskoch.movies.features.films.PAGED_LIST_CONFIG
import com.doskoch.movies.features.films_favourite.dataSource.FavouriteFilmsDataSource
import com.extensions.kotlin.functions.runAndWaitForEvent
import com.extensions.lifecycle.components.State
import com.extensions.lifecycle_test.functions.createTaskExecutor
import com.extensions.rx.components.schedulers.trampolineScheduler
import com.extensions.rx.functions.initSchedulersHandler
import com.extensions.rx.functions.resetSchedulersHandler
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.invoke
import io.mockk.mockk
import io.mockk.slot
import io.mockk.spyk
import io.mockk.verify
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertSame
import kotlin.test.assertTrue

class FavouriteFilmsViewModelTest {

    val module = spyk(
        FavouriteFilmsViewModelModule(
            dbRepository = mockk(),
            pagedListConfig = PAGED_LIST_CONFIG
        )
    )

    lateinit var viewModel: FavouriteFilmsViewModel

    @BeforeEach
    fun beforeEach() {
        ArchTaskExecutor.getInstance().setDelegate(createTaskExecutor())
        initSchedulersHandler(trampolineScheduler)

        viewModel = FavouriteFilmsViewModel(module)
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

        val observer = mockk<Observer<State<PagedList<Film>>>>(relaxed = true)

        @Test
        @DisplayName("An observer is notified with State.Loading")
        fun loadingState() {
            every { module.dbRepository.observeChanges() } returns Flowable.never()

            viewModel.pagedListData().observeForever(observer)

            val slot = slot<State.Loading<PagedList<Film>>>()
            verify { observer.onChanged(capture(slot)) }
            assertTrue(slot.isCaptured)
            assertFalse(slot.captured.isConsumed)
            assertNull(slot.captured.arguments)
            assertNull(slot.captured.data)
        }

        @Test
        @DisplayName("An observer is notified with State.Success")
        fun successState() {
            val totalCount = PAGED_LIST_CONFIG.pageSize
            val dataSource = mockk<FavouriteFilmsDataSource>(relaxed = true)

            every { module.dbRepository.observeChanges() } returns
                Flowable.just(setOf(DbFavouriteFilm::class))
            every { module.dbRepository.count() } returns Single.just(totalCount)
            every { module.createDataSource(totalCount, any()) } returns dataSource

            runAndWaitForEvent {
                every { observer.onChanged(match { it is State.Success }) } answers { it() }
                viewModel.pagedListData().observeForever(observer)
            }

            val slot = slot<State.Success<PagedList<Film>>>()
            verify { observer.onChanged(capture(slot)) }
            assertTrue(slot.isCaptured)
            assertFalse(slot.captured.isConsumed)
            assertNull(slot.captured.arguments)
            assertSame(PAGED_LIST_CONFIG, slot.captured.data.config)
            assertSame(dataSource, slot.captured.data.dataSource)
        }

        @Nested
        @DisplayName("An observer is notified with State.Failure")
        inner class FailureState {

            @Test
            @DisplayName("Error in dbRepository::observeChanges")
            fun errorInObserveChanges() {
                val exception = Exception("test")
                every { module.dbRepository.observeChanges() } returns Flowable.error(exception)

                runAndWaitForEvent {
                    every { observer.onChanged(match { it is State.Failure }) } answers { it() }
                    viewModel.pagedListData().observeForever(observer)
                }

                val slot = slot<State.Failure<PagedList<Film>>>()
                verify { observer.onChanged(capture(slot)) }
                assertTrue(slot.isCaptured)
                assertFalse(slot.captured.isConsumed)
                assertNull(slot.captured.arguments)
                assertNull(slot.captured.data)
                assertSame(exception, slot.captured.throwable)
            }

            @Test
            @DisplayName("Error in dbRepository::count")
            fun errorInCount() {
                val exception = Exception("test")

                every { module.dbRepository.observeChanges() } returns
                    Flowable.just(setOf(DbFavouriteFilm::class))
                every { module.dbRepository.count() } returns Single.error(exception)

                runAndWaitForEvent {
                    every { observer.onChanged(match { it is State.Failure }) } answers { it() }
                    viewModel.pagedListData().observeForever(observer)
                }

                val slot = slot<State.Failure<PagedList<Film>>>()
                verify { observer.onChanged(capture(slot)) }
                assertTrue(slot.isCaptured)
                assertFalse(slot.captured.isConsumed)
                assertNull(slot.captured.arguments)
                assertNull(slot.captured.data)
                assertSame(exception, slot.captured.throwable)
            }

            @Test
            @DisplayName("Error in module::createDataSource")
            fun errorInCreateDataSource() {
                val exception = Exception("test")

                every { module.dbRepository.observeChanges() } returns
                    Flowable.just(setOf(DbFavouriteFilm::class))
                every { module.dbRepository.count() } returns Single.just(0)
                every { module.createDataSource(any(), any()) } throws exception

                runAndWaitForEvent {
                    every { observer.onChanged(match { it is State.Failure }) } answers { it() }
                    viewModel.pagedListData().observeForever(observer)
                }

                val slot = slot<State.Failure<PagedList<Film>>>()
                verify { observer.onChanged(capture(slot)) }
                assertTrue(slot.isCaptured)
                assertFalse(slot.captured.isConsumed)
                assertNull(slot.captured.arguments)
                assertNull(slot.captured.data)
                assertSame(exception, slot.captured.throwable)
            }

            @Test
            @DisplayName("Error in dataSource::loadRange")
            fun onLoadRangeError() {
                val dataSource = mockk<FavouriteFilmsDataSource>(relaxed = true)
                val exception = Exception("test")

                val errorHandler = slot<(Throwable) -> Unit>()
                every { module.dbRepository.observeChanges() } returns
                    Flowable.just(setOf(DbFavouriteFilm::class))
                every { module.dbRepository.count() } returns Single.just(0)
                every { module.createDataSource(any(), capture(errorHandler)) } returns dataSource

                runAndWaitForEvent {
                    every { observer.onChanged(match { it is State.Success }) } answers { it() }
                    viewModel.pagedListData().observeForever(observer)
                }

                runAndWaitForEvent {
                    every { observer.onChanged(match { it is State.Failure }) } answers { it() }
                    errorHandler.invoke(exception)
                }

                val slot = slot<State.Failure<PagedList<Film>>>()
                verify { observer.onChanged(capture(slot)) }
                assertTrue(slot.isCaptured)
                assertFalse(slot.captured.isConsumed)
                assertNull(slot.captured.arguments)
                assertNull(slot.captured.data)
                assertSame(exception, slot.captured.throwable)
            }
        }
    }

    @Nested
    @DisplayName("delete(Film) works as expected")
    inner class Delete {

        val observer = mockk<Observer<State<Any>>>(relaxed = true)

        @Test
        @DisplayName("An observer is notified with State.Loading")
        fun onLoading() {
            val item = mockk<Film>()
            every { module.dbRepository.delete(item) } returns Completable.never()

            runAndWaitForEvent {
                every { observer.onChanged(match { it is State.Loading }) } answers { it() }
                viewModel.delete(item).observeForever(observer)
            }

            val slot = slot<State.Loading<Any>>()
            verify { observer.onChanged(capture(slot)) }
            assertTrue(slot.isCaptured)
            assertFalse(slot.captured.isConsumed)
            assertNull(slot.captured.arguments)
            assertNull(slot.captured.data)
        }

        @Test
        @DisplayName("An observer is notified with State.Success")
        fun onSuccess() {
            val item = mockk<Film>()
            every { module.dbRepository.delete(item) } returns Completable.complete()

            runAndWaitForEvent {
                every { observer.onChanged(match { it is State.Success }) } answers { it() }
                viewModel.delete(item).observeForever(observer)
            }

            val slot = slot<State.Success<Any>>()
            verify { observer.onChanged(capture(slot)) }
            assertTrue(slot.isCaptured)
            assertFalse(slot.captured.isConsumed)
            assertNull(slot.captured.arguments)
            assertNotNull(slot.captured.data)
        }

        @Test
        @DisplayName("An observer is notified with State.Failure")
        fun onError() {
            val item = mockk<Film>()
            val exception = Exception("test")
            every { module.dbRepository.delete(item) } returns Completable.error(exception)

            runAndWaitForEvent {
                every { observer.onChanged(match { it is State.Failure }) } answers { it() }
                viewModel.delete(item).observeForever(observer)
            }

            val slot = slot<State.Failure<Any>>()
            verify { observer.onChanged(capture(slot)) }
            assertTrue(slot.isCaptured)
            assertFalse(slot.captured.isConsumed)
            assertNull(slot.captured.arguments)
            assertNull(slot.captured.data)
            assertSame(exception, slot.captured.throwable)
        }
    }
}
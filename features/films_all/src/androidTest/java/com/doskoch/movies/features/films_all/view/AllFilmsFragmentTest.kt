package com.doskoch.movies.features.films_all.view

import android.content.Context
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.swipeDown
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.scrollTo
import androidx.test.espresso.matcher.ViewMatchers.hasSibling
import androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.isEnabled
import androidx.test.espresso.matcher.ViewMatchers.withChild
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.doskoch.movies.core.components.ui.base.recyclerView.paging.FetchStateViewHolder
import com.doskoch.movies.core.components.ui.base.recyclerView.paging.boundaryCallback.BoundaryCallbackResult
import com.doskoch.movies.database.modules.films.view.Film
import com.doskoch.movies.features.films.PAGED_LIST_CONFIG
import com.doskoch.movies.features.films_all.R
import com.doskoch.movies.features.films_all.dataSource.AllFilmsDataSource
import com.doskoch.movies.features.films_all.viewModel.AllFilmsViewModel
import com.extensions.android.components.ui.ContentManager
import com.extensions.android.components.ui.ContentManager.State.CONTENT
import com.extensions.android.components.ui.ContentManager.State.PLACEHOLDER
import com.extensions.android.components.ui.ContentManager.State.PROGRESS
import com.extensions.android_test.functions.createCondition
import com.extensions.android_test.functions.onSnackbar
import com.extensions.android_test.functions.wait
import com.extensions.android_test.matchers.IsRefreshingMatcher.Companion.isRefreshing
import com.extensions.lifecycle.components.State
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.invoke
import io.mockk.mockk
import io.mockk.slot
import io.mockk.spyk
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.not
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.runner.RunWith
import java.util.concurrent.Executor
import kotlin.random.Random

@RunWith(AndroidJUnit4ClassRunner::class)
class AllFilmsFragmentTest {

    companion object {
        lateinit var originProvideModule: (AllFilmsFragment) -> AllFilmsFragmentModule

        @JvmStatic
        @BeforeAll
        fun beforeAll() {
            originProvideModule = AllFilmsFragment.provideModule
        }
    }

    val module = spyk(
        AllFilmsFragmentModule(
            viewModelFactory = mockk(),
            shareFilm = mockk()
        )
    )
    val viewModel = mockk<AllFilmsViewModel>(relaxed = true)
    val dataSource = spyk(AllFilmsDataSource(mockk(relaxed = true)))

    lateinit var pagedListData: MutableLiveData<State<PagedList<Film>>>
    lateinit var boundaryCallbackData: MutableLiveData<BoundaryCallbackResult<Film>>
    lateinit var nextPageData: MutableLiveData<State<Int>>
    lateinit var refreshData: MutableLiveData<State<Any>>
    lateinit var switchFavouriteData: MutableLiveData<State<Any>>

    lateinit var scenario: FragmentScenario<AllFilmsFragment>

    @BeforeEach
    fun beforeEach() {
        AllFilmsFragment.provideModule = { module }
        initViewModel()
    }

    private fun initViewModel() {
        every {
            module.viewModelFactory.create()
            module.viewModelFactory.create(AllFilmsViewModel::class.java)
        } returns viewModel

        initLiveData()
    }

    private fun initLiveData() {
        pagedListData = MutableLiveData()
        boundaryCallbackData = MutableLiveData()
        nextPageData = MutableLiveData()
        refreshData = MutableLiveData()
        switchFavouriteData = MutableLiveData()

        every { viewModel.pagedListData() } returns
            pagedListData.apply { postValue(State.Loading()) }
        every { viewModel.boundaryCallbackData() } returns
            boundaryCallbackData
        every { viewModel.loadNextPage() } returns
            nextPageData.apply { postValue(State.Loading()) }
        every { viewModel.refresh() } returns
            refreshData.apply { postValue(State.Loading()) }
        every { viewModel.switchFavourite(any()) } returns
            switchFavouriteData.apply { postValue(State.Loading()) }
    }

    @AfterEach
    fun afterEach() {
        AllFilmsFragment.provideModule = originProvideModule
        clearAllMocks()
    }

    private fun launchFragment() {
        scenario = launchFragmentInContainer(themeResId = R.style.AppTheme_Light) {
            AllFilmsFragment()
        }
    }

    private fun buildPagedList(): PagedList<Film> {
        return PagedList(
            dataSource,
            PAGED_LIST_CONFIG,
            Executor(Runnable::run),
            Executor(Runnable::run),
            null,
            null
        )
    }

    private fun loadPage(totalCount: Int) {
        initDataSource(totalCount)
        val items = dataSource.get(totalCount, 0)

        pagedListData.postValue(State.Success(buildPagedList()))
        boundaryCallbackData.postValue(
            if (totalCount <= 0) {
                BoundaryCallbackResult.OnZeroItemsLoaded()
            } else {
                BoundaryCallbackResult.OnItemAtEndLoaded(items.last())
            }
        )
        if (totalCount > PAGED_LIST_CONFIG.pageSize) {
            nextPageData.postValue(State.Success(totalCount - PAGED_LIST_CONFIG.pageSize))
        }

        if (totalCount > 0) {
            createCondition { onView(withId(R.id.recyclerView)).check(matches(isDisplayed())) }
                .wait()
        }
    }

    private fun initDataSource(totalCount: Int) {
        val limit = slot<Int>()
        val offset = slot<Int>()

        every { dataSource.totalCount } returns totalCount
        every { dataSource.get(capture(limit), capture(offset)) } answers {
            testSuite(limit.captured, offset.captured + 1L)
        }
    }

    private fun testSuite(count: Int, firstId: Long): List<Film> {
        return mutableListOf<Film>().apply {
            var id = firstId
            for (i in 0L until count) {
                add(createFilm(id++))
            }
        }
    }

    private fun createFilm(id: Long): Film {
        return Film(
            id = id,
            posterPath = null,
            title = "title $id",
            overview = "overview $id",
            releaseDate = id * 1_000,
            isFavourite = Random.Default.nextBoolean()
        )
    }

    @Nested
    @DisplayName("Appropriate state is displayed to a user")
    inner class ContentManaging {

        @Test
        @DisplayName("Progress is displayed, until not empty list is loaded")
        fun progressOnListLoading() {
            launchFragment()

            assertState(PROGRESS)

            loadPage(PAGED_LIST_CONFIG.pageSize)

            assertState(CONTENT)
        }

        @Test
        @DisplayName("Progress is displayed, until the first page is loaded")
        fun progressOnFirstPageLoading() {
            launchFragment()
            loadPage(0)

            assertState(PROGRESS)

            loadPage(PAGED_LIST_CONFIG.pageSize)

            assertState(CONTENT)
        }

        @Test
        @DisplayName("Placeholder is displayed, when failed to load a list")
        fun placeholderOnFailure() {
            launchFragment()
            pagedListData.postValue(State.Failure(Exception()))

            assertState(PLACEHOLDER)
        }

        @Test
        @DisplayName("Placeholder is displayed, when first page is empty")
        fun placeholderOnEmptyList() {
            launchFragment()
            loadPage(0)
            nextPageData.postValue(State.Success(0))

            assertState(PLACEHOLDER)
        }

        @Test
        @DisplayName("Placeholder is displayed, when an error occurs during first page loading")
        fun placeholderOnFirstPageFailure() {
            launchFragment()
            loadPage(0)
            nextPageData.postValue(State.Failure(Exception()))

            assertState(PLACEHOLDER)
        }

        private fun assertState(state: ContentManager.State) {
            onView(withId(R.id.progressView))
                .check(matches(if (state == PROGRESS) isDisplayed() else not(isDisplayed())))
            onView(withId(R.id.recyclerView))
                .check(matches(if (state == CONTENT) isDisplayed() else not(isDisplayed())))
            onView(withId(R.id.placeholder))
                .check(matches(if (state == PLACEHOLDER) isDisplayed() else not(isDisplayed())))
        }
    }

    @Nested
    @DisplayName("SwipeRefreshLayout behaves as expected")
    inner class SwipeRefresh {

        @Test
        @DisplayName("SwipeRefreshLayout is disabled, while Progress is displayed")
        fun disableRefreshOnProgress() {
            launchFragment()

            onView(withId(R.id.swipeRefreshLayout)).check(matches(not(isEnabled())))
        }

        @Test
        @DisplayName("SwipeRefreshLayout shows indicator, until successful refresh")
        fun successRefresh() {
            launchFragment()
            loadPage(PAGED_LIST_CONFIG.pageSize)

            onView(withId(R.id.swipeRefreshLayout))
                .perform(swipeDown())
                .check(matches(isRefreshing()))

            refreshData.postValue(State.Success(Any()))

            onView(withId(R.id.swipeRefreshLayout)).check(matches(not(isRefreshing())))
        }

        @Test
        @DisplayName("SwipeRefreshLayout shows indicator, until failure in refresh occurs")
        fun failedRefresh() {
            launchFragment()
            loadPage(PAGED_LIST_CONFIG.pageSize)

            onView(withId(R.id.swipeRefreshLayout))
                .perform(swipeDown())
                .check(matches(isRefreshing()))

            refreshData.postValue(State.Failure(Exception()))

            onView(withId(R.id.swipeRefreshLayout)).check(matches(not(isRefreshing())))

            val errorText = ApplicationProvider.getApplicationContext<Context>()
                .getString(R.string.error_unknown)
            onSnackbar().check(matches(allOf(isDisplayed(), withText(errorText))))
        }
    }

    @Nested
    @DisplayName("Paging is working correctly")
    inner class Paging {

        @Test
        @DisplayName(
            "Loading item is displayed, while loading next page, and is hidden, when it is loaded"
        )
        fun showLoading() {
            launchFragment()
            loadPage(PAGED_LIST_CONFIG.pageSize)

            onView(withId(R.id.recyclerView))
                .perform(scrollTo<FetchStateViewHolder<*>>(withChild(withId(R.id.progressView))))

            onView(allOf(isDescendantOfA(withId(R.id.recyclerView)), withId(R.id.progressView)))
                .check(matches(isDisplayed()))

            loadPage(PAGED_LIST_CONFIG.pageSize * 2)

            val condition = createCondition {
                onView(allOf(isDescendantOfA(withId(R.id.recyclerView)), withId(R.id.progressView)))
                    .check(doesNotExist())
            }

            assertDoesNotThrow { condition.wait() }
        }

        @Test
        @DisplayName(
            """
                Error item is displayed, when error occurs during loading next page, and is hidden, 
                when it is loaded
            """
        )
        fun showError() {
            launchFragment()
            loadPage(PAGED_LIST_CONFIG.pageSize)
            nextPageData.postValue(State.Failure(Exception()))

            onView(withId(R.id.recyclerView))
                .perform(scrollTo<FetchStateViewHolder<*>>(withChild(withId(R.id.errorTextView))))

            onView(allOf(isDescendantOfA(withId(R.id.recyclerView)), withId(R.id.errorTextView)))
                .check(matches(isDisplayed()))

            loadPage(PAGED_LIST_CONFIG.pageSize)

            val condition = createCondition {
                onView(
                    allOf(isDescendantOfA(withId(R.id.recyclerView)), withId(R.id.errorTextView))
                )
                    .check(doesNotExist())
            }

            assertDoesNotThrow { condition.wait() }
        }
    }

    @Nested
    @DisplayName("Item could be added to or removed from favourites")
    inner class Favourite {

        @Test
        @DisplayName(
            """
                Corresponding viewModel function is called, and a Snackbar with error is displayed, 
                when it emits State.Failure
            """
        )
        fun showError() {
            launchFragment()
            loadPage(PAGED_LIST_CONFIG.pageSize)
            onView(
                allOf(
                    isDescendantOfA(withId(R.id.recyclerView)),
                    withId(R.id.favouriteButton),
                    hasSibling(withText(dataSource.get(1, 0)[0].title))
                )
            )
                .perform(click())
            switchFavouriteData.postValue(State.Failure(Exception()))

            val errorText = ApplicationProvider.getApplicationContext<Context>()
                .getString(R.string.error_unknown)
            onSnackbar().check(matches(allOf(isDisplayed(), withText(errorText))))
        }
    }

    @Nested
    @DisplayName("Item could be shared")
    inner class Share {

        @Test
        @DisplayName(
            """
                Corresponding viewModel function is called, and a Snackbar with error is displayed, 
                when it emits State.Failure
            """
        )
        fun showError() {
            val shareFilm =
                mockk<(AllFilmsFragment, Film, (Throwable) -> Unit) -> Unit>(relaxed = true)
            every { module.shareFilm } returns shareFilm

            val slot = slot<(Throwable) -> Unit>()
            every { shareFilm(any(), any(), capture(slot)) } answers { slot.invoke(Exception()) }

            launchFragment()
            loadPage(PAGED_LIST_CONFIG.pageSize)
            onView(
                allOf(
                    isDescendantOfA(withId(R.id.recyclerView)),
                    withId(R.id.shareButton),
                    hasSibling(withText(dataSource.get(1, 0)[0].title))
                )
            )
                .perform(click())

            val errorText = ApplicationProvider.getApplicationContext<Context>()
                .getString(R.string.error_unknown)
            onSnackbar().check(matches(allOf(isDisplayed(), withText(errorText))))
        }
    }
}
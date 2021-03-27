package com.doskoch.movies.features.films_favourite.view

import android.content.Context
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.hasSibling
import androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.doskoch.movies.database.modules.films.view.Film
import com.doskoch.movies.features.films.PAGED_LIST_CONFIG
import com.doskoch.movies.features.films_favourite.R
import com.doskoch.movies.features.films_favourite.dataSource.FavouriteFilmsDataSource
import com.doskoch.movies.features.films_favourite.viewModel.FavouriteFilmsViewModel
import com.extensions.android.components.ui.ContentManager
import com.extensions.android.components.ui.ContentManager.State.CONTENT
import com.extensions.android.components.ui.ContentManager.State.PLACEHOLDER
import com.extensions.android.components.ui.ContentManager.State.PROGRESS
import com.extensions.android_test.functions.createCondition
import com.extensions.android_test.functions.onSnackbar
import com.extensions.android_test.functions.wait
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
import org.junit.runner.RunWith
import java.util.concurrent.Executor
import kotlin.random.Random

@RunWith(AndroidJUnit4ClassRunner::class)
class FavouriteFilmsFragmentTest {

    companion object {
        lateinit var originProvideModule: (FavouriteFilmsFragment) -> FavouriteFilmsFragmentModule

        @JvmStatic
        @BeforeAll
        fun beforeAll() {
            originProvideModule = FavouriteFilmsFragment.provideModule
        }
    }

    val module = spyk(
        FavouriteFilmsFragmentModule(
            viewModelFactory = mockk(),
            shareFilm = mockk()
        )
    )
    val viewModel = mockk<FavouriteFilmsViewModel>(relaxed = true)
    val dataSource = spyk(FavouriteFilmsDataSource(mockk(relaxed = true)))

    lateinit var pagedListData: MutableLiveData<State<PagedList<Film>>>
    lateinit var deleteData: MutableLiveData<State<Any>>

    lateinit var scenario: FragmentScenario<FavouriteFilmsFragment>

    @BeforeEach
    fun beforeEach() {
        FavouriteFilmsFragment.provideModule = { module }
        initViewModel()
    }

    private fun initViewModel() {
        every {
            module.viewModelFactory.create()
            module.viewModelFactory.create(FavouriteFilmsViewModel::class.java)
        } returns viewModel

        initLiveData()
    }

    private fun initLiveData() {
        pagedListData = MutableLiveData()
        deleteData = MutableLiveData()

        every { viewModel.pagedListData() } returns
            pagedListData.apply { postValue(State.Loading()) }
        every { viewModel.delete(any()) } returns
            deleteData.apply { postValue(State.Loading()) }
    }

    @AfterEach
    fun afterEach() {
        FavouriteFilmsFragment.provideModule = originProvideModule
        clearAllMocks()
    }

    private fun launchFragment() {
        scenario = launchFragmentInContainer(themeResId = R.style.AppTheme_Light) {
            FavouriteFilmsFragment()
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
        pagedListData.postValue(State.Success(buildPagedList()))

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
        @DisplayName("Placeholder is displayed, when failed to load list")
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
    @DisplayName("Item could be removed from favourites")
    inner class Favourite {

        @Test
        @DisplayName(
            "A Snackbar with error is displayed, if an error occurred while deleting an item"
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
            deleteData.postValue(State.Failure(Exception()))

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
                mockk<(FavouriteFilmsFragment, Film, (Throwable) -> Unit) -> Unit>(relaxed = true)
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
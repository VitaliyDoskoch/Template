package com.doskoch.movies.features.splash.view

import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import androidx.navigation.Navigation.setViewNavController
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.doskoch.movies.features.splash.R
import com.doskoch.movies.features.splash.viewModel.SplashViewModel
import com.extensions.lifecycle.components.State
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class SplashFragmentTest {

    companion object {
        lateinit var originProvideModule: (SplashFragment) -> SplashFragmentModule

        @JvmStatic
        @BeforeAll
        fun beforeAll() {
            originProvideModule = SplashFragment.provideModule
        }
    }

    val module = spyk(
        SplashFragmentModule(
            viewModelFactory = mockk(),
            directions = mockk(relaxed = true),
            versionCode = 1
        )
    )
    val viewModel = mockk<SplashViewModel>(relaxed = true)


    lateinit var displayTimeoutData: MutableLiveData<State<Any>>
    lateinit var scenario: FragmentScenario<SplashFragment>

    @BeforeEach
    fun beforeEach() {
        SplashFragment.provideModule = { module }
        initViewModel()
    }

    private fun initViewModel() {
        every {
            module.viewModelFactory.create()
            module.viewModelFactory.create(SplashViewModel::class.java)
        } returns viewModel

        initLiveData()
    }

    private fun initLiveData() {
        displayTimeoutData = MutableLiveData()

        every { viewModel.displayTimeoutData() } returns
            displayTimeoutData.apply { postValue(State.Loading()) }
    }

    @AfterEach
    fun afterEach() {
        SplashFragment.provideModule = originProvideModule
        clearAllMocks()
    }

    @Nested
    @DisplayName("Navigation works as expected")
    inner class Navigate {

        val navController = mockk<NavController>(relaxed = true)

        @Test
        @DisplayName(
            "Navigate to directions.toMain(), when displayTimeOutData() emits State.Success"
        )
        fun navigateOnSuccess() {
            launchFragment()
            displayTimeoutData.postValue(State.Success(Any()))

            val directions = module.directions
            verify { navController.navigate(directions.toMain()) }
        }

        @Test
        @DisplayName(
            "Navigate to directions.toMain(), when displayTimeoutData() emits State.Failure"
        )
        fun navigateOnFailure() {
            launchFragment()
            displayTimeoutData.postValue(State.Failure(Exception()))

            val directions = module.directions
            verify { navController.navigate(directions.toMain()) }
        }

        private fun launchFragment() {
            scenario = launchFragmentInContainer(themeResId = R.style.AppTheme_Light) {
                SplashFragment()
            }
            scenario.onFragment {
                setViewNavController(it.requireView(), navController)
            }
        }
    }
}
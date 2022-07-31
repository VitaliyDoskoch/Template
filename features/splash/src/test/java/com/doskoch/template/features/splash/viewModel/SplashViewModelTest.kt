package com.doskoch.template.features.splash.viewModel

import androidx.arch.core.executor.ArchTaskExecutor
import com.doskoch.template.features.splash.MIN_SPLASH_DISPLAY_TIME
import com.extensions.lifecycle.components.State
import com.extensions.lifecycle_test.components.StateTestHelper
import com.extensions.lifecycle_test.functions.createTaskExecutor
import com.extensions.rx.components.schedulers.trampolineScheduler
import com.extensions.rx.functions.initSchedulersHandler
import com.extensions.rx.functions.resetSchedulersHandler
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockkStatic
import io.mockk.spyk
import io.reactivex.Flowable
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.util.concurrent.TimeUnit
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertSame

class SplashViewModelTest {

    val module = spyk(SplashViewModelModule(MIN_SPLASH_DISPLAY_TIME))

    lateinit var viewModel: SplashViewModel

    @BeforeEach
    fun beforeEach() {
        ArchTaskExecutor.getInstance().setDelegate(createTaskExecutor())
        initSchedulersHandler(trampolineScheduler)

        viewModel = SplashViewModel(module)
    }

    @AfterEach
    fun afterEach() {
        ArchTaskExecutor.getInstance().setDelegate(null)
        resetSchedulersHandler()
        clearAllMocks()
    }

    @Nested
    @DisplayName("displayTimeoutData() works as expected")
    inner class DisplayTimeoutData {

        @Test
        @DisplayName("An observer is notified with State.Loading")
        fun loadingState() {
            val time = module.minDisplayTime

            mockkStatic(Flowable::class)
            every { Flowable.timer(time, TimeUnit.MILLISECONDS) } returns Flowable.never()

            StateTestHelper { viewModel.displayTimeoutData() }
                .capture<State.Loading<Any>> {
                    it.assertPureState()
                    assertNull(it.captured.data)
                }
        }

        @Test
        @DisplayName("An observer is notified with State.Success")
        fun successState() {
            val time = module.minDisplayTime

            mockkStatic(Flowable::class)
            every { Flowable.timer(time, TimeUnit.MILLISECONDS) } returns Flowable.just(0)

            StateTestHelper { viewModel.displayTimeoutData() }
                .capture<State.Success<Any>> {
                    it.assertPureState()
                    assertNotNull(it.captured.data)
                }
        }

        @Test
        @DisplayName("An observer is notified with State.Failure")
        fun failureState() {
            val time = module.minDisplayTime
            val exception = Exception("test")

            mockkStatic(Flowable::class)
            every { Flowable.timer(time, TimeUnit.MILLISECONDS) } returns Flowable.error(exception)

            StateTestHelper { viewModel.displayTimeoutData() }
                .capture<State.Failure<Any>> {
                    it.assertPureState()
                    assertNull(it.captured.data)
                    assertSame(exception, it.captured.throwable)
                }
        }
    }
}

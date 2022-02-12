package com.doskoch.movies.features.splash.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.doskoch.movies.core.components.rx.RxViewModel
import io.reactivex.Flowable
import io.reactivex.disposables.Disposable
import timber.log.Timber
import java.util.concurrent.TimeUnit

class SplashViewModel(private val dependencies: Dependencies) : RxViewModel() {

    data class Dependencies(val minDisplayTime: Long)

}
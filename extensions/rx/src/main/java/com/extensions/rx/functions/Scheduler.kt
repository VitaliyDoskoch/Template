package com.extensions.rx.functions

import io.reactivex.Scheduler
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins

fun initSchedulersHandler(scheduler: Scheduler) {
    RxJavaPlugins.setInitNewThreadSchedulerHandler { scheduler }
    RxJavaPlugins.setInitIoSchedulerHandler { scheduler }
    RxJavaPlugins.setInitComputationSchedulerHandler { scheduler }
    RxJavaPlugins.setInitSingleSchedulerHandler { scheduler }
    RxAndroidPlugins.setInitMainThreadSchedulerHandler { scheduler }
}

fun resetSchedulersHandler() {
    RxJavaPlugins.reset()
    RxAndroidPlugins.reset()
}
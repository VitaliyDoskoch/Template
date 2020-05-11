package com.doskoch.movies.core.components.ui.base.activity

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import com.doskoch.movies.core.components.rx.DisposablesManager
import io.reactivex.disposables.Disposable

abstract class BaseActivity : AppCompatActivity() {

    private val disposablesManager by lazy { DisposablesManager { lifecycle } }

    fun disposeOn(event: Lifecycle.Event, disposable: Disposable) {
        disposablesManager.disposeOn(event, disposable)
    }

    fun disposeUpToEvent(event: Lifecycle.Event) {
        disposablesManager.disposeUpToEvent(event)
    }

}
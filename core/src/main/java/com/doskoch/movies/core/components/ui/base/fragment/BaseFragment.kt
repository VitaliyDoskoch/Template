package com.doskoch.movies.core.components.ui.base.fragment

import android.os.Bundle
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LiveData
import com.doskoch.movies.core.components.rx.DisposablesManager
import com.extensions.android.components.ui.ContentManager
import com.extensions.lifecycle.components.DataObserver
import com.extensions.lifecycle.components.ResultObserver
import com.extensions.lifecycle.components.State
import io.reactivex.disposables.Disposable

abstract class BaseFragment : Fragment() {

    @Suppress("PropertyName")
    val INSTANCE_STATE_KEYS = "${this::class.java.simpleName}_INSTANCE_STATE_KEYS"

    val instanceState by lazy { mutableMapOf<String, Any?>() }

    var contentManager: ContentManager? = null
        get() {
            return field ?: view?.let { ContentManager(it as? ViewGroup) }?.also { field = it }
        }
        private set

    private val disposablesManager by lazy { DisposablesManager { lifecycle } }

    fun disposeOn(event: Lifecycle.Event, disposable: Disposable) {
        disposablesManager.disposeOn(event, disposable)
    }

    fun disposeUpToEvent(event: Lifecycle.Event) {
        disposablesManager.disposeUpToEvent(event)
    }

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        savedInstanceState?.let { bundle ->
            bundle.getStringArray(INSTANCE_STATE_KEYS)?.forEach { key ->
                instanceState[key] = bundle.get(key)
            }
        }
    }

    @CallSuper
    override fun onDestroyView() {
        super.onDestroyView()
        contentManager = null
    }

    @CallSuper
    override fun onSaveInstanceState(outState: Bundle) {
        outState.putStringArray(INSTANCE_STATE_KEYS, instanceState.keys.toTypedArray())
        outState.putAll(bundleOf(*instanceState.toList().toTypedArray()))
        super.onSaveInstanceState(outState)
    }

    fun <T : Any> LiveData<State<T>>.observeResult(action: (state: State<T>) -> Unit) {
        observe(viewLifecycleOwner, ResultObserver(this, action))
    }

    fun <T : Any> LiveData<State<T>>.observeData(action: (state: State<T>) -> Unit) {
        observe(viewLifecycleOwner, DataObserver(action))
    }

    /**
     * Should return true to prevent Activity from handling its own onBackPressed.
     * Works only if Activity handle this situation (handling should be implemented manually).
     */
    open fun onBackPressed(): Boolean = false

}
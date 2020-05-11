package com.doskoch.movies.core.components.ui.base.dialog

import android.app.Dialog
import android.os.Bundle
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.view.Window
import androidx.annotation.CallSuper
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LiveData
import com.doskoch.movies.core.components.rx.DisposablesManager
import com.extensions.android.components.ui.ContentManager
import com.extensions.lifecycle.components.DataObserver
import com.extensions.lifecycle.components.ResultObserver
import com.extensions.lifecycle.components.State
import io.reactivex.disposables.Disposable

abstract class BaseDialog : DialogFragment() {

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

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState).apply {
            window?.requestFeature(Window.FEATURE_NO_TITLE)
            window?.setBackgroundDrawable(null)
        }
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(MATCH_PARENT, WRAP_CONTENT)
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

    override fun dismiss() {
        if (!isStateSaved) {
            super.dismiss()
        }
    }

    fun <T : Any> LiveData<State<T>>.observeResult(action: (state: State<T>) -> Unit) {
        observe(viewLifecycleOwner, ResultObserver(this, action))
    }

    fun <T : Any> LiveData<State<T>>.observeData(action: (state: State<T>) -> Unit) {
        observe(viewLifecycleOwner, DataObserver(action))
    }

}
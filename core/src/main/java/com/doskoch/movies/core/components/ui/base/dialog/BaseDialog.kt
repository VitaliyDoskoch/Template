package com.doskoch.movies.core.components.ui.base.dialog

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.view.Window
import androidx.annotation.CallSuper
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.viewbinding.ViewBinding
import com.doskoch.legacy.android.content.ContentManager

abstract class BaseDialog<V : ViewBinding> : DialogFragment() {

    @Suppress("PropertyName")
    val INSTANCE_STATE_KEYS = "${this::class.java.simpleName}_INSTANCE_STATE_KEYS"
    val instanceState by lazy { mutableMapOf<String, Any?>() }

    var viewBinding: V? = null
        private set
    var contentManager: ContentManager? = null
        get() {
            return field ?: view?.let { ContentManager(it as? ViewGroup) }?.also { field = it }
        }
        private set

    abstract fun inflateViewBinding(inflater: LayoutInflater): V?

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

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflateViewBinding(inflater).also { viewBinding = it }?.root
    }

    @CallSuper
    override fun onDestroyView() {
        super.onDestroyView()
        viewBinding = null
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
}
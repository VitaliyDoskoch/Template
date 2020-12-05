package com.doskoch.movies.core.components.ui.base.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.extensions.android.components.ui.ContentManager

abstract class BaseFragment<V : ViewBinding> : Fragment() {

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

    /**
     * Should return true to prevent Activity from handling its own onBackPressed.
     * Works only if Activity handle this situation (handling should be implemented manually).
     */
    open fun onBackPressed(): Boolean = false

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        savedInstanceState?.let { bundle ->
            bundle.getStringArray(INSTANCE_STATE_KEYS)?.forEach { key ->
                instanceState[key] = bundle.get(key)
            }
        }
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
}
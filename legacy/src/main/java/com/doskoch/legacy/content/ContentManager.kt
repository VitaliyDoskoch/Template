package com.doskoch.legacy.content

import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.core.view.isVisible
import androidx.transition.Fade
import androidx.transition.Transition
import com.doskoch.legacy.functions.withTransition

/**
 * Is a class, which helps to display only one state of page simultaneously.
 * See [ContentManager.State].
 * @param [rootView] [ViewGroup], that contains managed [View]s.
 * @param [isAnimationEnabled] whether transition animation is enabled. True by default.
 */
class ContentManager(var rootView: ViewGroup? = null, var isAnimationEnabled: Boolean = true) {

    enum class State {
        PROGRESS,
        CONTENT,
        PLACEHOLDER
    }

    val stateListeners by lazy { mutableListOf<(state: State) -> Unit>() }
    val progressListeners by lazy { mutableListOf<(show: Boolean) -> Unit>() }
    val contentListeners by lazy { mutableListOf<(show: Boolean) -> Unit>() }
    val placeholderListeners by lazy { mutableListOf<(show: Boolean) -> Unit>() }

    var progressView: View? = null
    var contentView: View? = null
    var placeholderView: View? = null

    /**
     * Uses passed ids to find corresponding Views in rootView to use them as elements
     * of ContentManager.
     * @param [progressViewRes] id of [View], that is used to show progress.
     * @param [contentViewRes] id of [View], that is used as content.
     * @param [placeholderViewRes] id of [View], that is used as a placeholder.
     */
    fun initViews(@IdRes progressViewRes: Int?,
                  @IdRes contentViewRes: Int?,
                  @IdRes placeholderViewRes: Int?) {
        progressViewRes?.let {
            progressView = rootView?.findViewById(it)
        }
        contentViewRes?.let {
            contentView = rootView?.findViewById(it)
        }
        placeholderViewRes?.let {
            placeholderView = rootView?.findViewById(it)
        }
    }

    fun showProgress(transition: Transition? = Fade(),
                     consumer: ((view: View?) -> Unit)? = null) {
        consumer?.invoke(progressView)

        stateListeners.forEach { it.invoke(State.PROGRESS) }

        onShowContent(false, transition)
        onShowPlaceholder(false, transition)
        onShowProgress(true, transition)
    }

    fun showContent(transition: Transition? = Fade(),
                    consumer: ((view: View?) -> Unit)? = null) {
        consumer?.invoke(contentView)

        stateListeners.forEach { it.invoke(State.CONTENT) }

        onShowProgress(false, transition)
        onShowPlaceholder(false, transition)
        onShowContent(true, transition)
    }

    fun showPlaceholder(transition: Transition? = Fade(),
                        consumer: ((view: View?) -> Unit)? = null) {
        consumer?.invoke(placeholderView)

        stateListeners.forEach { it.invoke(State.PLACEHOLDER) }

        onShowProgress(false, transition)
        onShowContent(false, transition)
        onShowPlaceholder(true, transition)
    }

    private fun onShowProgress(show: Boolean, transition: Transition?) {
        progressListeners.forEach { it.invoke(show) }

        if (progressView?.isVisible != show) {
            val rootView = this.rootView
            val action = { progressView?.isVisible = show }

            if (rootView != null && isAnimationEnabled && transition != null) {
                withTransition(rootView, transition, false, fun ViewGroup.() { action() })
            } else {
                action()
            }
        }
    }

    private fun onShowContent(show: Boolean, transition: Transition?) {
        contentListeners.forEach { it.invoke(show) }

        if (contentView?.isVisible != show) {
            val rootView = this.rootView
            val action = { contentView?.isVisible = show }

            if (rootView != null && isAnimationEnabled && transition != null) {
                withTransition(rootView, transition, false, fun ViewGroup.() { action() })
            } else {
                action()
            }
        }
    }

    private fun onShowPlaceholder(show: Boolean, transition: Transition?) {
        placeholderListeners.forEach { it.invoke(show) }

        if (placeholderView?.isVisible != show) {
            val rootView = this.rootView
            val action = { placeholderView?.isVisible = show }

            if (rootView != null && isAnimationEnabled && transition != null) {
                withTransition(rootView, transition, false, fun ViewGroup.() { action() })
            } else {
                action()
            }
        }
    }
}
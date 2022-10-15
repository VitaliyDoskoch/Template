package com.doskoch.template.core.functions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import timber.log.Timber

fun ViewModel.launchAction(action: suspend () -> Unit, onError: suspend (Throwable) -> Unit) = viewModelScope.launch {
    try {
        action.invoke()
    } catch (t: Throwable) {
        Timber.e(t)
        onError(t)
    }
}
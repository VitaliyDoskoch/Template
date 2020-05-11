package com.extensions.android.components.ui.simplifiedListeners

import android.text.Editable
import android.text.TextWatcher

/**
 * It is just a stub with overridden methods.
 */
interface SimpleTextWatcher : TextWatcher {

    override fun afterTextChanged(s: Editable?) {}

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
}
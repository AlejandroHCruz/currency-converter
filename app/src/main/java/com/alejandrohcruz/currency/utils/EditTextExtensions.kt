package com.alejandrohcruz.currency.utils

import android.view.inputmethod.EditorInfo
import android.widget.EditText

fun EditText.onImeActionDone(callback: () -> Unit) {
    setOnEditorActionListener { _, actionId, _ ->
        when (actionId) {
            EditorInfo.IME_ACTION_DONE,
            EditorInfo.IME_ACTION_NEXT,
            EditorInfo.IME_ACTION_PREVIOUS -> {
                callback.invoke()
                true
            }
        }
        false
    }
}
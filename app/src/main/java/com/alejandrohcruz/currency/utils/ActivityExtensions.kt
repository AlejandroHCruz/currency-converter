package com.alejandrohcruz.currency.utils

import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity

fun AppCompatActivity?.hideSoftKeyboard() {

    if (this?.currentFocus != null) {

        val inputManager = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        val windowToken = this.currentFocus?.windowToken

        if (windowToken != null) inputManager.hideSoftInputFromWindow(windowToken, 0)
    }
}
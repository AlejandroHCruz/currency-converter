package com.alejandrohcruz.currency.utils

import android.view.View
import com.alejandrohcruz.currency.R

/**
 * Enables or disables the wavy effect on Android 5 and above.
 */
fun View.setRippleEffectEnabled(enabled: Boolean) {
    // Material Design sweetness!
    if (enabled) {
        // set the ripple effect
        setBackgroundResource(R.drawable.ripple_bounded_drawable)
    } else {
        // remove the ripple effect
        setBackgroundResource(0)
    }
}
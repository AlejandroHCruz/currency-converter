package com.alejandrohcruz.currency.utils

import android.view.View
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.alejandrohcruz.currency.R
import com.google.android.material.snackbar.Snackbar

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

//region snack bar
/**
 * Transforms static java function Snackbar.make() to an extension function on View.
 */
fun View.showSnackbar(snackbarText: String, timeLength: Int) {
    Snackbar.make(this, snackbarText, timeLength).run {
        addCallback(object : Snackbar.Callback() {
            override fun onShown(sb: Snackbar?) {
                EspressoIdlingResource.increment()
            }

            override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                EspressoIdlingResource.decrement()
            }
        })
        setBackgroundTint(resources.getColor(R.color.colorPrimaryBlack))
        show()
    }
}

/**
 * Triggers a snackbar message when the value contained by snackbarTaskMessageLiveEvent is modified.
 */
fun View.setupSnackbar(
    lifecycleOwner: LifecycleOwner,
    snackbarEvent: LiveData<Event<Int>>,
    timeLength: Int
) {

    snackbarEvent.observe(lifecycleOwner, Observer { event ->
        event.getContentIfNotHandled()?.let {
            context.getString(it).let { text ->
                if (text.isNotBlank()) {
                    showSnackbar(text, timeLength)
                }
            }
        }
    })
}
//endregion

fun View.showToast(
    lifecycleOwner: LifecycleOwner,
    snackbarEvent: LiveData<Event<Any>>,
    timeLength: Int
) {

    snackbarEvent.observe(lifecycleOwner, Observer { event ->
        event.getContentIfNotHandled()?.let {
            when (it) {
                is String -> Toast.makeText(context, it, timeLength).show()
                is Int -> Toast.makeText(context, context.getString(it), timeLength).show()
                else -> {
                }
            }
        }
    })
}
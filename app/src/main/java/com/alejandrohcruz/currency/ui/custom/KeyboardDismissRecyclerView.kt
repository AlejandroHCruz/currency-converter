package com.alejandrohcruz.currency.ui.custom

import android.content.Context
import android.util.AttributeSet
import android.view.inputmethod.InputMethodManager
import androidx.recyclerview.widget.RecyclerView


/**
 * Implementation of [RecyclerView] that will dismiss keyboard when scrolling.
 *
 * @author milan
 */
class KeyboardDismissingRecyclerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = -1
) :
    RecyclerView(context, attrs, defStyle) {
    private var onKeyboardDismissingScrollListener: OnScrollListener? = null

    /**
     * Returns an [InputMethodManager]
     *
     * @return input method manager
     */
    var inputMethodManager: InputMethodManager? = null
        get() {
            if (null == field) {
                field =
                    context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            }
            return field
        }
        private set

    /**
     * Creates [OnScrollListener] that will dismiss keyboard when scrolling if the keyboard
     * has not been dismissed internally before
     */
    private fun setOnKeyboardDismissingListener() {
        onKeyboardDismissingScrollListener = object : OnScrollListener() {
            var isKeyboardDismissedByScroll = false
            override fun onScrollStateChanged(
                recyclerView: RecyclerView,
                state: Int
            ) {
                when (state) {
                    SCROLL_STATE_DRAGGING -> if (!isKeyboardDismissedByScroll) {
                        hideKeyboard()
                        isKeyboardDismissedByScroll = !isKeyboardDismissedByScroll
                    }
                    SCROLL_STATE_IDLE -> isKeyboardDismissedByScroll = false
                }
            }
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        onKeyboardDismissingScrollListener?.let { addOnScrollListener(it) }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        onKeyboardDismissingScrollListener?.let { removeOnScrollListener(it) }
    }

    /**
     * Hides the keyboard
     */
    fun hideKeyboard() {
        inputMethodManager?.hideSoftInputFromWindow(windowToken, 0)
        clearFocus()
    }

    init {
        setOnKeyboardDismissingListener()
    }
}
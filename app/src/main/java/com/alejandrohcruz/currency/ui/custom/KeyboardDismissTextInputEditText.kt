package com.alejandrohcruz.currency.ui.custom

import android.content.Context
import android.util.AttributeSet
import android.view.KeyEvent
import com.google.android.material.textfield.TextInputEditText

/**
 * This class overrides the onKeyPreIme method to dispatch a key event if the
 * KeyEvent passed to onKeyPreIme has a key code of KeyEvent.KEYCODE_BACK.
 * This allows key event listeners to detect that the soft keyboard was
 * dismissed.
 *
 */
class KeyboardDismissTextInputEditText : TextInputEditText {

    //region required empty constructors
    constructor(context: Context?, attrs: AttributeSet?, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    )

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?) : super(context)
    //endregion

    override fun onKeyPreIme(keyCode: Int, event: KeyEvent): Boolean {
        if (event.keyCode == KeyEvent.KEYCODE_BACK) {
            dispatchKeyEvent(event)
            return false
        }
        return super.onKeyPreIme(keyCode, event)
    }
}
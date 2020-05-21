package com.alejandrohcruz.currency.ui.component.rates.adapter

import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.alejandrohcruz.currency.R
import com.alejandrohcruz.currency.databinding.RowRateBinding
import com.alejandrohcruz.currency.model.CurrencyEnum
import com.alejandrohcruz.currency.ui.base.listeners.RecyclerItemListener
import com.alejandrohcruz.currency.utils.*
import java.math.BigDecimal
import java.math.RoundingMode

class RateViewHolder(private val itemBinding: RowRateBinding) :
    RecyclerView.ViewHolder(itemBinding.root) {

    //region properties
    private var recyclerItemListener: RecyclerItemListener? = null
    private val primaryBlackColor = ContextCompat.getColor(itemBinding.root.context, R.color.colorPrimaryBlack)
    private val primaryGreyColor = ContextCompat.getColor(itemBinding.root.context, R.color.colorPrimaryGrey)

    // Percentage of the original exchange rate to display
    private var baseMultiplier = 1.toBigDecimal()
    // Exchange rate that relates to the base currency
    private var conversionRate = 0.toBigDecimal()
    //endregion

    //region textWatcher & keyListener properties
    private val textWatcher = object: TextWatcher {

        //region required, unused overwritten methods
        override fun beforeTextChanged(
            s: CharSequence, start: Int, count: Int, after: Int) {
            // Intentionally left empty
        }

        override fun onTextChanged(
            s: CharSequence, start: Int, before: Int, count: Int) {
            // Intentionally left empty
        }
        //endregion

        override fun afterTextChanged(s: Editable) {

            itemBinding.currencyAmountInputLayout.editText?.apply {

                //region set or remove hint as text
                // When no text, add a 0 to have that for the user
                if (s.isBlank() || s.isEmpty()) {
                    s.insert(0, "0")
                } else {
                    // When typing a natural number after having a 0, remove the appended zero
                    if (s.length == 2 && s.first() == '0' && s[1] != '.') {
                        s.delete(0, 1)
                    }
                }
                //endregion

                // Text Colors
                s.toString().toBigDecimalOrNull()?.let { handleEditTextTextColor(it) }

                //region update base multiplier if was manipulated by the user (hasFocus flag)
                if (s.isNotBlank() && s.isNotEmpty() && hasFocus()) {
                    s.toString().toFloatOrNull()?.let {
                        // Define multiplier in units of the base currency
                        val newBaseMultiplier = ((1F / conversionRate.toFloat()) * it).toBigDecimal()
                        if (newBaseMultiplier != baseMultiplier) {
                            // Set multiplier in the UI thread, as it will refresh the UI
                            itemBinding.root.post {
                                recyclerItemListener?.onBaseMultiplierChanged(newBaseMultiplier)
                            }
                        }
                    }
                }
                //endregion
            }
        }
    }

    private val keyListener = object: View.OnKeyListener {
        override fun onKey(v: View?, keyCode: Int, event: KeyEvent?): Boolean {
            if (event?.keyCode == KeyEvent.KEYCODE_BACK || keyCode == KeyEvent.KEYCODE_BACK) {
                v?.clearFocus()
                return true
            }
            return false
        }
    }
    //endregion

    fun bind(
        baseMultiplier: BigDecimal,
        currencyName: String,
        conversionRate: Double,
        recyclerItemListener: RecyclerItemListener
    ) {

        this.recyclerItemListener = recyclerItemListener
        this.conversionRate = conversionRate.toBigDecimal()

        itemBinding.apply {

            val currency = CurrencyEnum.fromString(currencyName)

            // Set the title
            currencyTitle.setText(
                StringResUtils.getStringResForCurrency(currency)
            )
            // Set the currency's name
            currencyDescription.text = currencyName

            // Set the value in the input field
            currencyAmountInputLayout.editText?.apply {

                val valueToDisplay = conversionRate.toBigDecimal().times(baseMultiplier)
                    .setScale(2, RoundingMode.HALF_UP)

                setText(valueToDisplay.toPresentableString())

                handleEditTextTextColor(valueToDisplay)

                addTextChangedListener(textWatcher)

                setOnFocusChangeListener { _, _ ->
                    if (hasFocus()) recyclerItemListener.onTextBeingEdited(adapterPosition)
                    else {
                        recyclerItemListener.onTextNotBeingEdited(adapterPosition)
                    }
                }

                //region clear focus when not editing anymore
                onImeActionDone { clearFocus() }

                setOnKeyListener(this@RateViewHolder.keyListener)
                //endregion
            }

            // Set the flag's Image
            itemBinding.currencyFlag.currencyIcon.setImageResource(
                DrawableResUtils.getDrawableResForCurrency(currency)
            )

            //region Container configuration
            root.setRippleEffectEnabled(true)

            // For reacting to user interactions, please
            // use the position from the holder, so it is the right/current one
            // Source: https://stackoverflow.com/a/55567038
            root.setOnClickListener {
                recyclerItemListener.onItemSelected(
                    currency,
                    adapterPosition,
                    currencyAmountInputLayout.editText?.text.toString().toBigDecimalOrNull()
                )
            }
            //endregion
        }
    }

    private fun handleEditTextTextColor(s: BigDecimal) {
        itemBinding.currencyAmountInputLayout.editText?.apply {
            if (s.toDouble() == 0.0) {
                // The zero should be grey
                if (textColors.defaultColor != primaryGreyColor) {
                    setTextColor(primaryGreyColor)
                }
            } else {
                // Any other number should be black
                if (textColors.defaultColor != primaryBlackColor) {
                    setTextColor(primaryBlackColor)
                }
            }
        }
    }
}


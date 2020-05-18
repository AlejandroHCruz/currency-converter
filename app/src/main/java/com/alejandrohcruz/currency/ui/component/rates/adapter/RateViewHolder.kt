package com.alejandrohcruz.currency.ui.component.rates.adapter

import android.text.Editable
import android.text.TextWatcher
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.alejandrohcruz.currency.R
import com.alejandrohcruz.currency.databinding.RowRateBinding
import com.alejandrohcruz.currency.model.CurrencyEnum
import com.alejandrohcruz.currency.ui.base.listeners.RecyclerItemListener
import com.alejandrohcruz.currency.utils.DrawableResUtils
import com.alejandrohcruz.currency.utils.StringResUtils
import com.alejandrohcruz.currency.utils.setRippleEffectEnabled
import com.alejandrohcruz.currency.utils.toPresentableString
import kotlinx.android.synthetic.main.flag_layout.view.*

class RateViewHolder(private val itemBinding: RowRateBinding) :
    RecyclerView.ViewHolder(itemBinding.root) {

    private var recyclerItemListener: RecyclerItemListener? = null
    private val primaryBlackColor = ContextCompat.getColor(itemBinding.root.context, R.color.colorPrimaryBlack)
    private val primaryGreyColor = ContextCompat.getColor(itemBinding.root.context, R.color.colorPrimaryGrey)

    private val textWatcher = object: TextWatcher {
        override fun beforeTextChanged(
            s: CharSequence, start: Int, count: Int, after: Int) {
            // Intentionally left empty
        }

        override fun onTextChanged(
            s: CharSequence, start: Int, before: Int, count: Int) {
            // Intentionally left empty
        }

        override fun afterTextChanged(s: Editable) {

            itemBinding.currencyAmountInputLayout.editText?.apply {

                //region hint
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

                //region handle text color
                if (s.toString() == "0") {
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
                //endregion
            }
            //endregion

        }
    }

    fun bind(
        currencyName: String,
        conversionRate: Double,
        recyclerItemListener: RecyclerItemListener
    ) {

        this.recyclerItemListener = recyclerItemListener

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
                setText(conversionRate.toPresentableString())
                hint = if (conversionRate == 0.0) "0"  else ""
                addTextChangedListener(textWatcher)
                setOnFocusChangeListener { _, _ ->
                    if (hasFocus()) recyclerItemListener.onTextBeingEdited(adapterPosition)
                    else {
                        recyclerItemListener.onTextNotBeingEdited(adapterPosition)
                    }
                }
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
                    adapterPosition
                )
            }
            //endregion
        }
    }
}


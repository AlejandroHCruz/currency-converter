package com.alejandrohcruz.currency.ui.component.rates.adapter

import androidx.recyclerview.widget.RecyclerView
import com.alejandrohcruz.currency.databinding.RowRateBinding
import com.alejandrohcruz.currency.model.CurrencyEnum
import com.alejandrohcruz.currency.ui.base.listeners.RecyclerItemListener
import com.alejandrohcruz.currency.utils.DrawableResUtils
import com.alejandrohcruz.currency.utils.StringResUtils
import com.alejandrohcruz.currency.utils.setRippleEffectEnabled
import com.alejandrohcruz.currency.utils.toPresentableString

class RateViewHolder(private val itemBinding: RowRateBinding) :
    RecyclerView.ViewHolder(itemBinding.root) {

    private var recyclerItemListener: RecyclerItemListener? = null
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


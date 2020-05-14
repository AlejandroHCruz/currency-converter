package com.alejandrohcruz.currency.ui.component.rates.adapter

import androidx.recyclerview.widget.RecyclerView
import com.alejandrohcruz.currency.databinding.RowRateBinding
import com.alejandrohcruz.currency.model.CurrencyEnum
import com.alejandrohcruz.currency.ui.base.listeners.RecyclerItemListener
import com.alejandrohcruz.currency.utils.DrawableResUtils
import com.alejandrohcruz.currency.utils.StringResUtils
import com.alejandrohcruz.currency.utils.setRippleEffectEnabled

class RateViewHolder(private val itemBinding: RowRateBinding) :
    RecyclerView.ViewHolder(itemBinding.root) {

    fun bind(
        currencyName: String,
        conversionRate: Double,
        recyclerItemListener: RecyclerItemListener
    ) {

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
                setText(conversionRate.toString())
                hint = if (conversionRate == 0.0) "0"  else ""
            }

            // Set the flag's Image
            itemBinding.currencyFlag.currencyIcon.setImageResource(
                DrawableResUtils.getDrawableResForCurrency(currency)
            )

            // Container configuration
            root.setRippleEffectEnabled(true)
            root.setOnClickListener { recyclerItemListener.onItemSelected(currency) }
        }
    }
}


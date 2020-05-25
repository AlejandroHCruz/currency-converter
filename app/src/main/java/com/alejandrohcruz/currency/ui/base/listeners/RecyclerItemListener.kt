package com.alejandrohcruz.currency.ui.base.listeners

import com.alejandrohcruz.currency.model.CurrencyEnum
import java.math.BigDecimal

interface RecyclerItemListener {
    fun onItemSelected(
        currency: CurrencyEnum,
        position: Int,
        newBaseMultiplier: Double?
    )
    fun onTextBeingEdited(position: Int)
    fun onTextNotBeingEdited(position: Int)
    fun onBaseMultiplierChanged(newBaseMultiplier: Double)
}

package com.alejandrohcruz.currency.ui.base.listeners

import com.alejandrohcruz.currency.model.CurrencyEnum

interface RecyclerItemListener {
    fun onItemSelected(currency: CurrencyEnum)
}

package com.alejandrohcruz.currency.data

import com.alejandrohcruz.currency.data.remote.dto.RatesModel
import com.alejandrohcruz.currency.model.CurrencyEnum

interface DataSource {
    suspend fun requestConversionRates(delayInMs: Long, baseCurrency: CurrencyEnum): Resource<RatesModel>
}

package com.alejandrohcruz.currency.data.remote

import com.alejandrohcruz.currency.data.Resource
import com.alejandrohcruz.currency.data.remote.dto.RatesModel
import com.alejandrohcruz.currency.model.CurrencyEnum

internal interface RemoteSource {
    suspend fun requestConversionRates(baseCurrency: CurrencyEnum): Resource<RatesModel>
}

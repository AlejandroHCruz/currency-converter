package com.alejandrohcruz.currency.data.remote

import com.alejandrohcruz.currency.data.Resource
import com.alejandrohcruz.currency.data.remote.dto.RatesModel

internal interface RemoteSource {
    suspend fun requestConversionRates(): Resource<RatesModel>
}

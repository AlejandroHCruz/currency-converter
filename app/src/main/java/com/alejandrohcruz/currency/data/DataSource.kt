package com.alejandrohcruz.currency.data

import com.alejandrohcruz.currency.data.remote.dto.RatesModel

interface DataSource {
    suspend fun requestConversionRates(): Resource<RatesModel>
}

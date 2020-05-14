package com.alejandrohcruz.currency.data.remote.service

import com.alejandrohcruz.currency.data.remote.dto.RatesModel
import retrofit2.Response
import retrofit2.http.GET

interface ConversionRatesService {
    @GET("latest?base=EUR")
    suspend fun fetchConversionRates(): Response<RatesModel>
}

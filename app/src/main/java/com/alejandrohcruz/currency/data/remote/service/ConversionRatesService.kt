package com.alejandrohcruz.currency.data.remote.service

import com.alejandrohcruz.currency.data.remote.dto.RatesModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ConversionRatesService {
    @GET("latest")
    suspend fun fetchConversionRates(@Query("base") baseCurrency: String): Response<RatesModel>
}

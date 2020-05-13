package com.alejandrohcruz.currency.data.remote.dto


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RatesModel(
    @Json(name = "baseCurrency")
    val baseCurrency: String = "",
    @Json(name = "rates")
    val ratesMap: Map<String, Double>? = null
)
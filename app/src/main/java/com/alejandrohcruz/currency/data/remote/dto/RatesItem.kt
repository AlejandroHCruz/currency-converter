package com.alejandrohcruz.currency.data.remote.dto


import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class RatesItem(
    val currencyName : String
) : Parcelable
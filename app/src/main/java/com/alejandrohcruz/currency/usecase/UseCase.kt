package com.alejandrohcruz.currency.usecase

import androidx.lifecycle.MutableLiveData
import com.alejandrohcruz.currency.data.Resource
import com.alejandrohcruz.currency.data.remote.dto.RatesModel

interface UseCase {
    fun getConversionRates()
    val ratesLiveData: MutableLiveData<Resource<RatesModel>>
}

package com.alejandrohcruz.currency.usecase

import androidx.lifecycle.MutableLiveData
import com.alejandrohcruz.currency.data.Resource
import com.alejandrohcruz.currency.data.remote.dto.RatesModel
import kotlinx.coroutines.Job

interface UseCase {
    fun getConversionRates(delayInMs: Long)
    fun stopGetConversionRatesJob()
    val ratesLiveData: MutableLiveData<Resource<RatesModel>>
    var currentJob: Job?
}

package com.alejandrohcruz.currency.usecase

import androidx.lifecycle.MutableLiveData
import com.alejandrohcruz.currency.data.Resource
import com.alejandrohcruz.currency.data.remote.dto.RatesModel
import com.alejandrohcruz.currency.model.CurrencyEnum
import kotlinx.coroutines.Job
import java.util.*

interface UseCase {
    fun getConversionRates(delayInMs: Long, baseCurrency: CurrencyEnum)
    fun stopGetConversionRatesJob()
    val remoteRatesLiveData: MutableLiveData<Resource<RatesModel>>
    var currentJob: Job?
}

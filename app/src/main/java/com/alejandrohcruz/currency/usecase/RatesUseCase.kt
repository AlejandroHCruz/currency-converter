package com.alejandrohcruz.currency.usecase

import androidx.lifecycle.MutableLiveData
import com.alejandrohcruz.currency.data.DataSource
import com.alejandrohcruz.currency.data.Resource
import com.alejandrohcruz.currency.data.error.Error.Companion.NETWORK_ERROR
import com.alejandrohcruz.currency.data.remote.dto.RatesModel
import com.alejandrohcruz.currency.model.CurrencyEnum
import com.alejandrohcruz.currency.utils.L
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class RatesUseCase @Inject
constructor(private val dataRepository: DataSource, override val coroutineContext: CoroutineContext) : UseCase, CoroutineScope {

    val TAG = this.javaClass.simpleName

    private val ratesMutableLiveData = MutableLiveData<Resource<RatesModel>>()
    override val ratesLiveData: MutableLiveData<Resource<RatesModel>> = ratesMutableLiveData
    override var currentJob: Job? = null

    override fun getConversionRates(delayInMs: Long, baseCurrency: CurrencyEnum) {
        // Sanity check: only one active job at a time
        if (currentJob == null || currentJob?.isActive == false) {
            var serviceResponse: Resource<RatesModel>?
            ratesMutableLiveData.postValue(Resource.Loading())
            currentJob = launch {
                try {
                    serviceResponse = dataRepository.requestConversionRates(delayInMs, baseCurrency)
                    ratesMutableLiveData.postValue(serviceResponse)
                } catch (e: Exception) {
                    ratesMutableLiveData.postValue(Resource.DataError(NETWORK_ERROR))
                }
            }
        }
    }

    override fun stopGetConversionRatesJob() {
        currentJob?.apply {
            if (isActive) {
                try {
                    currentJob?.cancel()
                } catch (e: CancellationException) {
                    e.message?.let { L.e(TAG, it) }
                }
            }
        }
        currentJob = null
    }
}

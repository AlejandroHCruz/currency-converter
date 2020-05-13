package com.alejandrohcruz.currency.usecase

import androidx.lifecycle.MutableLiveData
import com.alejandrohcruz.currency.data.DataSource
import com.alejandrohcruz.currency.data.Resource
import com.alejandrohcruz.currency.data.error.Error.Companion.NETWORK_ERROR
import com.alejandrohcruz.currency.data.remote.dto.RatesModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class RatesUseCase @Inject
constructor(private val dataRepository: DataSource, override val coroutineContext: CoroutineContext) : UseCase, CoroutineScope {
    private val conversionRatesMutableLiveData = MutableLiveData<Resource<RatesModel>>()
    override val ratesLiveData: MutableLiveData<Resource<RatesModel>> = conversionRatesMutableLiveData


    override fun getConversionRates() {
        var serviceResponse: Resource<RatesModel>?
        conversionRatesMutableLiveData.postValue(Resource.Loading())
        launch {
            try {
                serviceResponse = dataRepository.requestConversionRates()
                conversionRatesMutableLiveData.postValue(serviceResponse)
            } catch (e: Exception) {
                conversionRatesMutableLiveData.postValue(Resource.DataError(NETWORK_ERROR))
            }
        }
    }
}

package com.alejandrohcruz.currency.data.remote

import com.alejandrohcruz.currency.App
import com.alejandrohcruz.currency.data.Resource
import com.alejandrohcruz.currency.data.error.Error.Companion.DEFAULT_ERROR
import com.alejandrohcruz.currency.data.error.Error.Companion.NETWORK_ERROR
import com.alejandrohcruz.currency.data.error.Error.Companion.NO_INTERNET_CONNECTION
import com.alejandrohcruz.currency.data.local.LocalConverter.convertResponseToLocalBaseCurrency
import com.alejandrohcruz.currency.data.remote.dto.RatesModel
import com.alejandrohcruz.currency.data.remote.service.ConversionRatesService
import com.alejandrohcruz.currency.model.CurrencyEnum
import com.alejandrohcruz.currency.utils.isConnected
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject
import kotlin.reflect.KSuspendFunction1

class RemoteRepository @Inject
constructor(serviceGenerator: ServiceGenerator) : RemoteSource {

    private val service = serviceGenerator.createService(ConversionRatesService::class.java)

    override suspend fun requestConversionRates(baseCurrency: CurrencyEnum): Resource<RatesModel> {
        processCall(service::fetchConversionRates, baseCurrency)?.let { response ->
            return when (response) {
                is RatesModel -> {
                    val data = convertResponseToLocalBaseCurrency(baseCurrency, response)
                    Resource.Success(data = data)
                }
                else -> {
                    Resource.DataError(errorCode = response as Int)
                }
            }
        } ?: return Resource.DataError(errorCode = DEFAULT_ERROR)
    }

    private suspend fun processCall(
        responseCall: KSuspendFunction1<String, Response<RatesModel>>,
        baseCurrency: CurrencyEnum
    ): Any? {

        if (!App.context.isConnected()) {
            return NO_INTERNET_CONNECTION
        }
        return try {

            val currencyForCall = when (baseCurrency) {
                // Avoid using currencies that would cause others' conversion rate to be 0
                CurrencyEnum.KRW, CurrencyEnum.IDR -> CurrencyEnum.EUR.name
                else -> baseCurrency.name
            }

            // Get response's body or error code
            val response = responseCall.invoke(currencyForCall)
            if (response.isSuccessful) {
                response.body()
            } else {
                response.code()
            }
        } catch (e: IOException) {
            NETWORK_ERROR
        }
    }
}

package com.alejandrohcruz.currency.data.remote

import com.alejandrohcruz.currency.App
import com.alejandrohcruz.currency.data.Resource
import com.alejandrohcruz.currency.data.error.Error.Companion.NETWORK_ERROR
import com.alejandrohcruz.currency.data.error.Error.Companion.NO_INTERNET_CONNECTION
import com.alejandrohcruz.currency.data.remote.dto.RatesModel
import com.alejandrohcruz.currency.data.remote.service.ConversionRatesService
import com.alejandrohcruz.currency.utils.isConnected
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

class RemoteRepository @Inject
constructor(private val serviceGenerator: ServiceGenerator) : RemoteSource {

    override suspend fun requestConversionRates(): Resource<RatesModel> {
        val newsService = serviceGenerator.createService(ConversionRatesService::class.java)
        return when (val response = processCall(newsService::fetchConversionRates)) {
            is RatesModel -> {
                Resource.Success(data = response)
            }
            else -> {
                Resource.DataError(errorCode = response as Int)
            }
        }
    }

    private suspend fun processCall(responseCall: suspend () -> Response<*>): Any? {
        if (!App.context.isConnected()) {
            return NO_INTERNET_CONNECTION
        }
        return try {
            val response = responseCall.invoke()
            val responseCode = response.code()
            if (response.isSuccessful) {
                response.body()
            } else {
                responseCode
            }
        } catch (e: IOException) {
            NETWORK_ERROR
        }
    }
}

package com.alejandrohcruz.currency.data

import com.alejandrohcruz.currency.data.local.LocalRepository
import com.alejandrohcruz.currency.data.remote.RemoteRepository
import com.alejandrohcruz.currency.data.remote.dto.RatesModel
import com.alejandrohcruz.currency.model.CurrencyEnum
import kotlinx.coroutines.delay
import javax.inject.Inject

class DataRepository @Inject
constructor(private val remoteRepository: RemoteRepository, private val localRepository: LocalRepository) : DataSource {

    val cachedCurrencies = localRepository.cachedCurrencies

    override suspend fun requestConversionRates(delayInMs: Long, baseCurrency: CurrencyEnum): Resource<RatesModel> {
        delay(delayInMs)
        return remoteRepository.requestConversionRates(baseCurrency)
    }

    override suspend fun storeConversionRates(serviceResponse: Resource<RatesModel>) {
        localRepository.insertOrReplaceCurrencyConversionRates(serviceResponse)
    }

    override suspend fun storeBaseCurrency(newBaseCurrencyEnum: CurrencyEnum) {
        localRepository.reorderCurrenciesBasedOnNewBaseOne(newBaseCurrencyEnum)
    }

    // TODO: Retrieve on app start
}

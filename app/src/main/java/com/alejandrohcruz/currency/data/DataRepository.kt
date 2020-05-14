package com.alejandrohcruz.currency.data

import com.alejandrohcruz.currency.data.local.LocalRepository
import com.alejandrohcruz.currency.data.remote.RemoteRepository
import com.alejandrohcruz.currency.data.remote.dto.RatesModel
import javax.inject.Inject

class DataRepository @Inject
constructor(private val remoteRepository: RemoteRepository, private val localRepository: LocalRepository) : DataSource {

    override suspend fun requestConversionRates(): Resource<RatesModel> {
        return remoteRepository.requestConversionRates()
    }
}

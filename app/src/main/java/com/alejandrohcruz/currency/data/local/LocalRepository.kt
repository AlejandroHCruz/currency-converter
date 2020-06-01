package com.alejandrohcruz.currency.data.local

import com.alejandrohcruz.currency.data.Resource
import com.alejandrohcruz.currency.data.local.LocalConverter.convertToLocalDatabaseCurrencyObjects
import com.alejandrohcruz.currency.data.local.LocalConverter.reorderAndRecalculatedCurrenciesBasedOnNewBaseOne
import com.alejandrohcruz.currency.data.remote.dto.RatesModel
import com.alejandrohcruz.currency.model.BaseMultiplier
import com.alejandrohcruz.currency.model.Currency
import com.alejandrohcruz.currency.model.CurrencyEnum
import com.alejandrohcruz.currency.utils.L
import javax.inject.Inject

/**
 * Declares the DAO as a private property in the constructor. Inject or pass in the DAO
 * instead of the whole database, because you only need access to the DAO
 */
class LocalRepository @Inject
constructor(
    private val currencyDao: CurrencyDao,
    private val baseMultiplierDao: BaseMultiplierDao
) {

    //region base multiplier

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    val cachedBaseMultiplier = baseMultiplierDao.getUniqueBaseMultiplier()

    suspend fun insertOrReplaceBaseMultiplier(newBaseMultiplier: BaseMultiplier) {
        baseMultiplierDao.insertOrReplace(newBaseMultiplier)
    }

    //endregion

    //region currencies

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    val cachedCurrencies = currencyDao.getOrderedCurrenciesLiveData()

    suspend fun insertOrReplaceCurrencyConversionRates(remoteCurrencies: Resource<RatesModel>) {
        currencyDao.insertOrReplace(
            convertToLocalDatabaseCurrencyObjects(
                remoteCurrencies,
                cachedCurrencies.value ?: emptyList()
            )
        )
    }

    suspend fun reorderCurrenciesBasedOnNewBaseOne(newBaseCurrencyEnum: CurrencyEnum) {
        reorderAndRecalculatedCurrenciesBasedOnNewBaseOne(
            newBaseCurrencyEnum,
            cachedCurrencies.value
        )?.let {
            // Store new world (currencies) order
            updateCurrencies(it)
        }
    }

    private suspend fun updateCurrencies(remoteCurrencies: List<Currency>) {
        currencyDao.updateCurrencies(remoteCurrencies)
    }
    //endregion
}

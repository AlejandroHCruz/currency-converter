package com.alejandrohcruz.currency.data.local

import com.alejandrohcruz.currency.BuildConfig
import com.alejandrohcruz.currency.data.Resource
import com.alejandrohcruz.currency.data.remote.dto.RatesModel
import com.alejandrohcruz.currency.model.Currency
import com.alejandrohcruz.currency.model.CurrencyEnum
import com.alejandrohcruz.currency.utils.L

object LocalConverter {

    private val TAG = this.javaClass.simpleName

    fun convertToLocalDatabaseCurrencyObjects(
        remoteRates: Resource<RatesModel>,
        cachedCurrencies: List<Currency>
    ): List<Currency> {

        // Sanity check: Can only update the rates if there are any new incoming rates
        remoteRates.data?.apply {

            val ratesToStore = ArrayList<Currency>()

            if (!cachedCurrencies.isNullOrEmpty()) {

                // Update values, respecting the order
                cachedCurrencies.forEach { cached ->
                    val newRate =
                        if (ratesMap?.containsKey(cached.title) == true) {
                            ratesMap.getValue(cached.title)
                        } else {
                            cached.rate // expected for the base currency
                        }
                    ratesToStore.add(Currency(cached.title, newRate, cached.position))
                }

            } else {

                // Everything is new, grab the order from the incoming data from the remote

                // Base currency goes always at the top
                ratesToStore.add(Currency(baseCurrency, 1.0, 0))

                ratesMap?.keys?.forEachIndexed { index, it ->
                    ratesToStore.add(Currency(it, ratesMap.getValue(it), index.plus(1)))
                }
            }

            return ratesToStore
        }
        return cachedCurrencies
    }

    fun reorderAndRecalculatedCurrenciesBasedOnNewBaseOne(newBaseCurrencyEnum: CurrencyEnum,
                                                          cachedCurrencies: List<Currency>?): List<Currency>? {
        cachedCurrencies?.let { currencies ->

            val reorderedCurrencies = currencies.toMutableList()

            val previousBaseCurrency = currencies.find { it.position == 0 }
            val newBaseCurrency = currencies.find { it.title ==  newBaseCurrencyEnum.name }

            if (previousBaseCurrency == null || newBaseCurrency == null) {
                L.e(TAG, "Cannot recalculate currencies when some base ones are not found")
                return null
            }

            val indexOfNewBaseCurrency = currencies.indexOfFirst { it.title == newBaseCurrencyEnum.name }

            currencies.forEachIndexed { index, currency ->

                //region reorder
                val newPosition = when (currency.title) {
                    newBaseCurrencyEnum.name -> {
                        0
                    }
                    previousBaseCurrency.title -> {
                        1
                    }
                    else -> {
                        if (indexOfNewBaseCurrency > index) index.plus(1) else index
                    }
                }

                if (BuildConfig.DEBUG) {
                    check(newPosition <= CurrencyEnum.values().size)
                }
                //endregion

                val newRate = if (newPosition == 0)
                        1.0
                    else
                        1.div(newBaseCurrency.rate.div(currency.rate))

                // Update the required data (position)
                reorderedCurrencies[index] = Currency(currency.title, newRate, newPosition)
            }

            return reorderedCurrencies

        } ?: L.e(TAG, "Cannot reorder currencies when they haven't been saved before")

        return null
    }

    fun convertResponseToLocalBaseCurrency(
        localBaseCurrency: CurrencyEnum,
        remoteResponse: RatesModel
    ): RatesModel {

        return if (remoteResponse.baseCurrency == localBaseCurrency.name) {
            // Local & remote base currencies are as expected, go ahead
            remoteResponse
        } else {
            //region Convert to local base currency before continuing
            RatesModel(
                localBaseCurrency.name,
                mutableMapOf<String, Double>().apply {
                    remoteResponse.ratesMap?.let { rates ->
                        val values = rates.values

                        val newBaseMultiplier = 1.div(rates[localBaseCurrency.name] ?: 1.0)

                        // Remote base currency is not included, let's add it
                        this[remoteResponse.baseCurrency] = newBaseMultiplier

                        rates.keys.forEachIndexed { index, key ->
                            // this[key] = 1.div(values.elementAt(index)).times(newBaseMultiplier)
                            this[key] = 1.div(rates[localBaseCurrency.name] ?: 1.0)
                                .times(values.elementAt(index))
                        }

                        // Base currency should not be in the map
                        this.remove(localBaseCurrency.name)
                    }
                }
            )
            //endregion
        }
    }
}
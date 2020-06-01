package com.alejandrohcruz.currency.data.local

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

            currencies.forEachIndexed { index, currency ->

                //region reorder
                val newPosition = if (currency.title == newBaseCurrencyEnum.name) {
                    0
                } else currency.position.plus(1)
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
}
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

    fun reorderCurrenciesBasedOnNewBaseOne(newBaseCurrencyEnum: CurrencyEnum,
                                           cachedCurrencies: List<Currency>?): List<Currency>? {
        cachedCurrencies?.let {

            val reorderedCurrencies = it.toMutableList()

            it.forEachIndexed { index, currency ->
                val newPosition = if (currency.title == newBaseCurrencyEnum.name) {
                    0
                } else currency.position.plus(1)

                // Update the required data (position)
                reorderedCurrencies[index] = Currency(currency.title, currency.rate, newPosition)
            }

            return reorderedCurrencies

        } ?: L.e(TAG, "Cannot reorder currencies when they haven't been saved before")

        return null
    }
}
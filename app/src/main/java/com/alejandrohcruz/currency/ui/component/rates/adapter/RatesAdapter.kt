package com.alejandrohcruz.currency.ui.component.rates.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alejandrohcruz.currency.databinding.RowRateBinding
import com.alejandrohcruz.currency.model.CurrencyEnum
import com.alejandrohcruz.currency.ui.base.listeners.RecyclerItemListener
import com.alejandrohcruz.currency.ui.component.rates.activity.RatesViewModel
import com.alejandrohcruz.currency.utils.L
import java.util.*
import kotlin.collections.ArrayList

class RatesAdapter(
    private val ratesViewModel: RatesViewModel
) :
    RecyclerView.Adapter<RateViewHolder>() {

    //region properties
    private val TAG = this.javaClass.simpleName

    private var currencyNames: ArrayList<String> = ArrayList()
    private var conversionRates: ArrayList<Double> = ArrayList()
    //endregion

    private val onItemClickListener: RecyclerItemListener = object : RecyclerItemListener {
        override fun onItemSelected(currency: CurrencyEnum, position: Int) {

            Collections.swap(currencyNames, position, 0)
            Collections.swap(conversionRates, position, 0)

            notifyItemMoved(position, 0)

            ratesViewModel.setBaseCurrency(currency)
        }
    }

    //region bind / create ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RateViewHolder {
        val itemBinding = RowRateBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RateViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: RateViewHolder, position: Int) {
        if (conversionRates.size > position) {
            holder.bind(currencyNames[position], conversionRates[position], onItemClickListener)
        } else {
            L.e(
                TAG,
                "position: $position is larger than the size of rates: ${conversionRates.size}"
            )
        }
    }
    //endregion

    override fun getItemCount(): Int {
        return conversionRates.size
    }

    /**
     * Called when the data is updated in the ViewModel and the Activity is observing it
     */
    fun onRatesUpdated(rates: Map<String, Double>?) {

        rates?.apply {

            if (currencyNames.isEmpty() || conversionRates.isEmpty()) {

                //region Define the conversion rate and names for the first time!
                currencyNames = (listOf(ratesViewModel.baseCurrency.value?.name ?: "EUR")
                        + keys.toList()) as ArrayList<String>
                conversionRates = (listOf(1.0) + values.toList()) as ArrayList<Double>
                //endregion

            } else {

                // region update the conversion rates
                val orderIndex = ArrayList<Int>()
                currencyNames.forEachIndexed { _, currency ->
                    orderIndex.add(keys.indexOf(currency))
                }
                val valuesAsList = values.toList()
                orderIndex.forEachIndexed { index, indexValue ->
                    // Check that we have a valid index, since the base currency is always missing from the rates Map
                    conversionRates[index] = if (index >= 0 && indexValue >= 0) {
                        // Update the value
                        valuesAsList[indexValue]
                    } else {
                        if (currencyNames[index] == (ratesViewModel.baseCurrency.value?.name ?: CurrencyEnum.EUR.name)) {
                            // Base currency
                            1.0
                        } else {
                            // Unknown, keep unchanged
                            conversionRates[index].also { L.e(TAG, "NANI?") }
                        }
                    }
                }
                //endregion

            }
        }

        notifyDataSetChanged()
    }
}


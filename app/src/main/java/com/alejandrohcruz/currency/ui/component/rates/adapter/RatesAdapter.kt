package com.alejandrohcruz.currency.ui.component.rates.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alejandrohcruz.currency.databinding.RowRateBinding
import com.alejandrohcruz.currency.model.CurrencyEnum
import com.alejandrohcruz.currency.ui.base.listeners.RecyclerItemListener
import com.alejandrohcruz.currency.ui.component.rates.activity.RatesViewModel
import com.alejandrohcruz.currency.utils.L
import java.math.BigDecimal
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

    private var rowBeingEdited: Int? = null
    //endregion

    private val onItemInteractionListener: RecyclerItemListener = object : RecyclerItemListener {
        override fun onItemSelected(
            currency: CurrencyEnum,
            position: Int,
            newBaseMultiplier: BigDecimal?
        ) {

            Collections.swap(currencyNames, position, 0)
            Collections.swap(conversionRates, position, 0)

            notifyItemMoved(position, 0)

            ratesViewModel.setBaseCurrency(currency)
            ratesViewModel.setBaseMultiplier(newBaseMultiplier ?: 1.toBigDecimal())
        }

        override fun onTextBeingEdited(position: Int) {
            rowBeingEdited = position
        }

        override fun onTextNotBeingEdited(position: Int) {
            if (rowBeingEdited == position) rowBeingEdited = null
        }

        override fun onBaseMultiplierChanged(newBaseMultiplier: BigDecimal) {
            val previousBaseMultiplier = ratesViewModel.baseMultiplier.value
            if (newBaseMultiplier != previousBaseMultiplier) {
                ratesViewModel.setBaseMultiplier(newBaseMultiplier)
                if (previousBaseMultiplier != null) {
                    // Only refresh the UI after the first time the multiplier is defined
                    updateAllRowsExceptTheOneSelected()
                }
            }
        }
    }

    //region bind / create ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RateViewHolder {
        val itemBinding = RowRateBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RateViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: RateViewHolder, position: Int) {
        if (conversionRates.size > position) {
            holder.bind(
                ratesViewModel.baseMultiplier.value ?: 1.toBigDecimal(),
                currencyNames[position],
                conversionRates[position],
                onItemInteractionListener
            )
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

        updateAllRowsExceptTheOneSelected()
    }

    private fun updateAllRowsExceptTheOneSelected() {

        if (rowBeingEdited == null) {
            // Refresh all the elements
            notifyDataSetChanged()
        } else {
            // Don't refresh the row whose EditText is being edited
            rowBeingEdited?.let {
                val start: Int
                val end: Int
                val last = conversionRates.size.minus(1)

                when (it) {
                    -1 -> {
                        // invalid case
                        return
                    }
                    0 -> {
                        // don't refresh the first row
                        start = 1
                        end = last
                    }
                    last -> {
                        // don't refresh the last row
                        start = 0
                        end = last.minus(1)
                    }
                    else -> {
                        // Don't refresh a row in the middle by breaking the refresh in 2:
                        // 1. First part is from start until one before the selected one
                        start = 0
                        end = it.minus(1)
                        // 2. Second part is this the one below the selected one until the last one
                        notifyItemRangeChanged(it.plus(1), last)
                    }

                }
                if (start == end) {
                    notifyItemChanged(start)
                } else {
                    notifyItemRangeChanged(start, end)
                }
            }
        }
    }
}


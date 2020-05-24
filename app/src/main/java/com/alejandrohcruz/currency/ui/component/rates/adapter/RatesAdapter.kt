package com.alejandrohcruz.currency.ui.component.rates.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alejandrohcruz.currency.databinding.RowRateBinding
import com.alejandrohcruz.currency.model.Currency
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

    private var currenciesList: List<Currency> = ArrayList()

    private var rowBeingEdited: Int? = null
    //endregion

    private val onItemInteractionListener: RecyclerItemListener = object : RecyclerItemListener {
        override fun onItemSelected(
            currency: CurrencyEnum,
            position: Int,
            newBaseMultiplier: BigDecimal?
        ) {

            Collections.swap(currenciesList, position, 0)

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
        if (currenciesList.size > position) {
            holder.bind(
                ratesViewModel.baseMultiplier.value ?: 1.toBigDecimal(),
                currenciesList[position].title,
                currenciesList[position].rate,
                onItemInteractionListener
            )
        } else {
            L.e(
                TAG,
                "position: $position is larger than the size of rates: ${currenciesList.size}"
            )
        }
    }
    //endregion

    override fun getItemCount(): Int {
        return currenciesList.size
    }

    /**
     * Called when the data is updated in the ViewModel and the Activity is observing it
     */
    fun onRatesUpdated(nonEmptyCachedCurrencies: List<Currency>) {

        currenciesList = nonEmptyCachedCurrencies

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
                val last = currenciesList.size.minus(1)

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


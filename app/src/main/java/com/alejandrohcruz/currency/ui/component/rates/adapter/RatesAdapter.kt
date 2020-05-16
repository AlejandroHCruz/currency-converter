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

    private var rates: Map<String, Double>? = null

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
            L.e(TAG, "position: $position is larger than the size of rates: ${conversionRates.size}")
        }
    }
    //endregion

    override fun getItemCount(): Int {
        return conversionRates.size.plus(1)
    }

    /**
     * Called when the data is updated in the ViewModel and the Activity is observing it
     */
    fun onRatesUpdated(rates: Map<String, Double>?) {
        this.rates?.forEach {
            // TODO: update the values in lieu
            it.key
        }
        currencyNames =
            (arrayListOf(
                ratesViewModel.baseCurrency.value?.name ?: "EUR"
            ) + (rates?.keys?.toList()
                ?: emptyArray<String>().toList())) as ArrayList<String>
        conversionRates = (arrayListOf(1.0) + (rates?.values?.toList()
            ?: emptyArray<Double>().toList())) as ArrayList<Double>
        notifyDataSetChanged()
    }
}


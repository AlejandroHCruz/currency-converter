package com.alejandrohcruz.currency.ui.component.rates.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alejandrohcruz.currency.databinding.RowRateBinding
import com.alejandrohcruz.currency.model.CurrencyEnum
import com.alejandrohcruz.currency.ui.base.listeners.RecyclerItemListener
import com.alejandrohcruz.currency.ui.component.rates.activity.RatesViewModel
import com.alejandrohcruz.currency.utils.L
import com.alejandrohcruz.currency.utils.observe

class RatesAdapter(private val ratesViewModel: RatesViewModel) :
    RecyclerView.Adapter<RateViewHolder>() {

    //region properties
    private val TAG = this.javaClass.simpleName

    private var currencyNames: List<String> = emptyList()
    private var conversionRates: List<Double> = emptyList()
    //endregion

    private val onItemClickListener: RecyclerItemListener = object : RecyclerItemListener {
        override fun onItemSelected(currency: CurrencyEnum) {
            ratesViewModel.setBaseCurrency(currency)
        }
    }

    //region Bind/Create ViewHolder
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
        return conversionRates.size
    }

    /**
     * Called when the data is updated in the ViewModel and the Activity is observing it
     */
    fun onRatesUpdated(rates: Map<String, Double>?) {
        currencyNames = rates?.keys?.toList() ?: emptyArray<String>().toList()
        conversionRates = rates?.values?.toList() ?: emptyArray<Double>().toList()
        notifyDataSetChanged()
    }
}


package com.alejandrohcruz.currency.ui.component.rates.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alejandrohcruz.currency.databinding.RowRateBinding
import com.alejandrohcruz.currency.model.CurrencyEnum
import com.alejandrohcruz.currency.ui.base.listeners.RecyclerItemListener
import com.alejandrohcruz.currency.ui.component.rates.activity.RatesViewModel

class RatesAdapter(private val RatesViewModel: RatesViewModel, rates: Map<String, Double>?) :
    RecyclerView.Adapter<RateViewHolder>() {

    private val currencyNames: List<String> = rates?.keys?.toList() ?: emptyArray<String>().toList()
    private val conversionRates: List<Double> = rates?.values?.toList() ?: emptyArray<Double>().toList()

    private val onItemClickListener: RecyclerItemListener = object : RecyclerItemListener {
        override fun onItemSelected(currency: CurrencyEnum) {
            RatesViewModel.setBaseCurrency(currency)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RateViewHolder {
        val itemBinding = RowRateBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RateViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: RateViewHolder, position: Int) {
        if (conversionRates.size > position) {
                holder.bind(currencyNames[position], conversionRates[position], onItemClickListener)
        } else {
            // TODO: Log
        }
    }

    override fun getItemCount(): Int {
        return conversionRates.size
    }
}


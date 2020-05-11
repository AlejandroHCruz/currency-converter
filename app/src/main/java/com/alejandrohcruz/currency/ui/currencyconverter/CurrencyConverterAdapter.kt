package com.alejandrohcruz.currency.ui.currencyconverter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alejandrohcruz.currency.R
import com.alejandrohcruz.currency.model.Currency
import kotlinx.android.synthetic.main.row_rate.view.*

internal class CurrencyConverterAdapter(private val currencies: List<Currency>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        (holder as? CurrencyViewHolder)?.itemView?.apply {

            // Make sure we have the data we're attempting to display
            if (position <= currencies.size.minus(1)) {

                currency_title.text = currencies[position].title
                currency_description.text = currencies[position].description
                // currency_amount.text = currencies[position].rate.toString() TODO: Implement

            } else {
                // TODO: Show error
            }
        } ?: println("error") // TODO: Show error
    }

    override fun getItemCount() = currencies.size

    //region ViewHolder Definition
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        LayoutInflater.from(parent.context).inflate(R.layout.row_rate, parent, false).let { view ->
            return CurrencyViewHolder(view)
        }
    }

    private inner class CurrencyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    //endregion
}
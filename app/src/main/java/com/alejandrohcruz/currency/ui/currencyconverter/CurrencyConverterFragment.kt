package com.alejandrohcruz.currency.ui.currencyconverter

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.alejandrohcruz.currency.R
import com.alejandrohcruz.currency.model.Currency
import kotlinx.android.synthetic.main.currency_converter_fragment.currency_recycler_view as recyclerView

class CurrencyConverterFragment : Fragment() {

    //region Instantiation
    companion object {
        fun newInstance() = CurrencyConverterFragment()
    }
    //endregion

    //region Creation
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.currency_converter_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(activity)
            adapter = CurrencyConverterAdapter(listOf(Currency("USD", "US Dollar", 1L)))
        }
    }
    //endregion

    //region ViewModel
    private lateinit var viewModel: CurrencyConverterViewModel

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(CurrencyConverterViewModel::class.java)
        // TODO: Use the ViewModel
    }
    //endregion
}
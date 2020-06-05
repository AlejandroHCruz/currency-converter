package com.alejandrohcruz.currency.ui.component.rates.activity

import android.os.Bundle
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.test.espresso.IdlingResource
import com.alejandrohcruz.currency.data.Resource
import com.alejandrohcruz.currency.data.remote.dto.RatesModel
import com.alejandrohcruz.currency.databinding.RatesActivityBinding
import com.alejandrohcruz.currency.model.Currency
import com.alejandrohcruz.currency.viewmodel.ViewModelFactory
import com.alejandrohcruz.currency.ui.base.BaseActivity
import com.alejandrohcruz.currency.ui.component.rates.adapter.RatesAdapter
import com.alejandrohcruz.currency.utils.*
import com.alejandrohcruz.currency.utils.Constants.INSTANCE.DATA_REFRESH_DELAY
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject

class RatesActivity : BaseActivity() {
    private lateinit var binding: RatesActivityBinding

    @Inject
    lateinit var ratesViewModel: RatesViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    val countingIdlingResource: IdlingResource
        @VisibleForTesting
        get() = EspressoIdlingResource.idlingResource

    //region init
    override fun initViewBinding() {
        binding = RatesActivityBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

    override fun initializeViewModel() {
        ratesViewModel = viewModelFactory.create(ratesViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@RatesActivity)
            adapter = RatesAdapter(ratesViewModel)
            setHasFixedSize(true)
        }
    }
    //endregion

    //region lifecycle
    override fun onStart() {
        ratesViewModel.getConversionRates()
        super.onStart()
    }

    override fun onStop() {
        ratesViewModel.stopGettingConversionRates()
        super.onStop()
    }
    //endregion

    private fun bindListData(cachedCurrencies: List<Currency>) {
        if (cachedCurrencies.isNotEmpty()) {
            (binding.recyclerView.adapter as? RatesAdapter?)?.onRatesUpdated(cachedCurrencies)
            showDataView(true)
        } else {
            showDataView(false)
        }
        // FIXME: this breaks when loading from local on app start
        // EspressoIdlingResource.decrement()
    }

    private fun observeSnackBarMessages(event: LiveData<Event<Int>>) {
        binding.main.setupSnackbar(this, event, Snackbar.LENGTH_LONG)
    }

    private fun showDataView(show: Boolean) {
        /*
        binding.tvNoData.visibility = if (show) GONE else VISIBLE
        binding.rlNewsList.visibility = if (show) VISIBLE else GONE
        binding.pbLoading.toGone()
         */
    }

    private fun showLoadingView() {
        /*
        binding.pbLoading.toVisible()
        binding.tvNoData.toGone()
        binding.rlNewsList.toGone()
         */
        EspressoIdlingResource.increment()
    }

    private fun handleRemoteRatesPayload(ratesModel: Resource<RatesModel>) {
        when (ratesModel) {
            is Resource.Loading -> showLoadingView()
            is Resource.Success -> {
                // Refresh every second
                ratesViewModel.getConversionRates(DATA_REFRESH_DELAY)
            }
            is Resource.DataError -> {
                // TODO: Not true, only fail if it has no data at all to display
                showDataView(false)

                ratesModel.errorCode?.let { ratesViewModel.showRemoteRatesSnackbarMessage(it) }
                // Keep trying every second
                ratesViewModel.getConversionRates(DATA_REFRESH_DELAY)
            }
        }
    }

    override fun observeViewModel() {
        observe(ratesViewModel.volatileCurrenciesLiveData, ::handleCurrenciesChanged)
        observe(ratesViewModel.remoteRatesLiveData, ::handleRemoteRatesPayload)
        observeSnackBarMessages(ratesViewModel.showRemoteRatesSnackBar)
    }

    private fun handleCurrenciesChanged(cachedCurrencies: List<Currency>) {
        bindListData(cachedCurrencies)
    }
}

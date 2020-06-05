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
import com.alejandrohcruz.currency.model.ViewStateEnum
import com.alejandrohcruz.currency.viewmodel.ViewModelFactory
import com.alejandrohcruz.currency.ui.base.BaseActivity
import com.alejandrohcruz.currency.ui.component.rates.adapter.RatesAdapter
import com.alejandrohcruz.currency.utils.*
import com.alejandrohcruz.currency.utils.Constants.INSTANCE.DATA_REFRESH_DELAY
import com.google.android.material.snackbar.Snackbar
import com.shouquan.statelayout.StateLayout.Companion.STATE_CONTENT
import com.shouquan.statelayout.StateLayout.Companion.STATE_ERROR
import com.shouquan.statelayout.StateLayout.Companion.STATE_LOADING
import javax.inject.Inject

class RatesActivity : BaseActivity() {

    //region properties
    private lateinit var binding: RatesActivityBinding

    @Inject
    lateinit var ratesViewModel: RatesViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    val countingIdlingResource: IdlingResource
        @VisibleForTesting
        get() = EspressoIdlingResource.idlingResource
    //endregion

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
        ratesViewModel.viewStateLiveData.value?.let { handleViewState(it) }
    }

    override fun onStop() {
        ratesViewModel.stopGettingConversionRates()
        super.onStop()
    }
    //endregion

    //region view states
    private fun showContentView() {
        binding.layoutState.setState(STATE_CONTENT)
        EspressoIdlingResource.decrement()
    }

    private fun showLoadingView() {
        if (isConnected()) {
            binding.layoutState.setState(STATE_LOADING)
        }
        EspressoIdlingResource.increment()
    }

    private fun showErrorView() {
        binding.layoutState.setState(STATE_ERROR)
    }

    private fun handleViewState(viewStateEnum: ViewStateEnum) {
        when (viewStateEnum) {
            ViewStateEnum.LOADING -> showLoadingView()
            ViewStateEnum.CONTENT -> showContentView()
            ViewStateEnum.ERROR -> showErrorView()
        }
    }
    //endregion

    private fun bindListData(cachedCurrencies: List<Currency>) {
        if (cachedCurrencies.isNotEmpty()) {
            (binding.recyclerView.adapter as? RatesAdapter?)?.onRatesUpdated(cachedCurrencies)
        }
    }

    //region observe and handle live data
    private fun observeSnackBarMessages(event: LiveData<Event<Int>>) {
        binding.main.setupSnackbar(this, event, Snackbar.LENGTH_LONG)
    }

    private fun handleRemoteRatesPayload(ratesModel: Resource<RatesModel>) {
        when (ratesModel) {
            is Resource.Success -> {
                // Refresh every second
                ratesViewModel.getConversionRates(DATA_REFRESH_DELAY)
            }
            is Resource.DataError -> {
                ratesModel.errorCode?.let { ratesViewModel.showRemoteRatesSnackbarMessage(it) }
                // Keep trying every second
                ratesViewModel.getConversionRates(DATA_REFRESH_DELAY)
            }
        }
    }

    override fun observeViewModel() {
        observe(ratesViewModel.volatileCurrenciesLiveData, ::handleCurrenciesChanged)
        observe(ratesViewModel.remoteRatesLiveData, ::handleRemoteRatesPayload)
        observe(ratesViewModel.viewStateLiveData, ::handleViewState)
        observeSnackBarMessages(ratesViewModel.showRemoteRatesSnackBar)
    }

    private fun handleCurrenciesChanged(cachedCurrencies: List<Currency>) {
        bindListData(cachedCurrencies)
    }
    //endregion
}

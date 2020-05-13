package com.alejandrohcruz.currency.ui.component.rates.activity

import android.os.Bundle
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.test.espresso.IdlingResource
import com.alejandrohcruz.currency.R
import com.alejandrohcruz.currency.data.Resource
import com.alejandrohcruz.currency.data.remote.dto.RatesItem
import com.alejandrohcruz.currency.data.remote.dto.RatesModel
import com.alejandrohcruz.currency.databinding.RatesActivityBinding
import com.alejandrohcruz.currency.viewmodel.ViewModelFactory
import com.alejandrohcruz.currency.ui.base.BaseActivity
import com.alejandrohcruz.currency.ui.component.rates.adapter.RatesAdapter
import com.alejandrohcruz.currency.utils.EspressoIdlingResource
import com.alejandrohcruz.currency.utils.Event
import com.alejandrohcruz.currency.utils.observe
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

        if (savedInstanceState != null) {
            // TODO: Do something
        }

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@RatesActivity)
            setHasFixedSize(true)
        }

        ratesViewModel.getConversionRates()
    }

    private fun bindListData(RatesModel: RatesModel) {
        if (RatesModel.ratesMap != null) {

            binding.recyclerView.adapter = RatesAdapter(ratesViewModel, RatesModel.ratesMap)
            showDataView(true)
        } else {
            showDataView(false)
        }
        EspressoIdlingResource.decrement()
    }

    private fun observeSnackBarMessages(event: LiveData<Event<Int>>) {
        // binding.rlNewsList.setupSnackbar(this, event, Snackbar.LENGTH_LONG)
    }

    private fun observeToast(event: LiveData<Event<Any>>) {
        // binding.rlNewsList.showToast(this, event, Snackbar.LENGTH_LONG)
    }

    private fun showSearchError() {
        ratesViewModel.showSnackbarMessage(R.string.search_error)
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

    private fun showSearchResult(RatesItem: RatesItem) {
        // ratesViewModel.setBaseCurrency(RatesItem)
        // binding.pbLoading.toGone()
    }

    private fun noSearchResult(unit: Unit) {
        showSearchError()
        // binding.pbLoading.toGone()
    }

    private fun handleRatesPayload(RatesModel: Resource<RatesModel>) {
        when (RatesModel) {
            is Resource.Loading -> showLoadingView()
            is Resource.Success -> RatesModel.data?.let { bindListData(RatesModel = it) }
            is Resource.DataError -> {
                showDataView(false)
                RatesModel.errorCode?.let { ratesViewModel.showToastMessage(it) }
            }
        }
    }

    override fun observeViewModel() {
        observe(ratesViewModel.ratesLiveData, ::handleRatesPayload)
        observe(ratesViewModel.newsSearchFound, ::showSearchResult)
        observe(ratesViewModel.noSearchFound, ::noSearchResult)
        // TODO: Set base currency
        // observeEvent(ratesViewModel.setBaseCurrency, ::navigateToDetailsScreen)
        observeSnackBarMessages(ratesViewModel.showSnackBar)
        observeToast(ratesViewModel.showToast)
    }
}

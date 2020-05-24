package com.alejandrohcruz.currency.ui.component.rates.activity

import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.alejandrohcruz.currency.data.Resource
import com.alejandrohcruz.currency.data.error.mapper.ErrorMapper
import com.alejandrohcruz.currency.data.remote.dto.RatesModel
import com.alejandrohcruz.currency.data.remote.dto.RatesItem
import com.alejandrohcruz.currency.model.Currency
import com.alejandrohcruz.currency.model.CurrencyEnum
import com.alejandrohcruz.currency.ui.base.BaseViewModel
import com.alejandrohcruz.currency.usecase.RatesUseCase
import com.alejandrohcruz.currency.usecase.errors.ErrorManager
import com.alejandrohcruz.currency.utils.Event
import com.alejandrohcruz.currency.utils.L
import java.math.BigDecimal
import javax.inject.Inject

class RatesViewModel@Inject
constructor(private val ratesDataUseCase: RatesUseCase) : BaseViewModel() {

    private val TAG = this.javaClass.simpleName

    override val errorManager: ErrorManager
        get() = ErrorManager(ErrorMapper())

    //region LiveData properties
    /**
     * Data --> LiveData, Exposed as LiveData, Locally in viewModel as MutableLiveData
     */
    var ratesLiveData: MutableLiveData<Resource<RatesModel>> = ratesDataUseCase.ratesLiveData

    private val newsSearchFoundPrivate: MutableLiveData<RatesItem> = MutableLiveData()
    val newsSearchFound: LiveData<RatesItem> get() = newsSearchFoundPrivate

    private val noSearchFoundPrivate: MutableLiveData<Unit> = MutableLiveData()
    val noSearchFound: LiveData<Unit> get() = noSearchFoundPrivate

    private val baseCurrencyPrivate = MutableLiveData<CurrencyEnum>()
    val baseCurrency : LiveData<CurrencyEnum> get() = baseCurrencyPrivate

    private val baseMultiplierPrivate = MutableLiveData<BigDecimal>()
    val baseMultiplier : LiveData<BigDecimal> get() = baseMultiplierPrivate


    /**
     * UI actions as event, user action is single one time event, Shouldn't be multiple time consumption
     */
    private val setBaseCurrencyPrivate = MutableLiveData<Event<CurrencyEnum>>()
    val setBaseCurrency: LiveData<Event<CurrencyEnum>> get() = setBaseCurrencyPrivate

    /**
     * Error handling as UI
     */
    private val showSnackBarPrivate = MutableLiveData<Event<Int>>()
    val showSnackBar: LiveData<Event<Int>> get() = showSnackBarPrivate

    private val showToastPrivate = MutableLiveData<Event<Any>>()
    val showToast: LiveData<Event<Any>> get() = showToastPrivate
    //endregion

    //region Observer properties
    /**
     * Store the new rates when they change
     */
    private val ratesObserver = Observer<Resource<RatesModel>> {
        if (it is Resource.Success) {
            storeConversionRates(it)
        }
    }

    private val cachedCurrenciesObserver = Observer<List<Currency>> {
        L.i(TAG, "Data was successfully saved: $it")
    }
    //endregion

    //region lifecycle
    init {
        ratesLiveData.observeForever(ratesObserver)
        ratesDataUseCase.cachedCurrenciesLiveData.observeForever(cachedCurrenciesObserver)
    }

    /**
     * This method will be called when this ViewModel is no longer used and will be destroyed.
     *
     *
     * It is useful when ViewModel observes some data and you need to clear this subscription to
     * prevent a leak of this ViewModel.
     */
    override fun onCleared() {
        ratesLiveData.removeObserver(ratesObserver)
        ratesDataUseCase.cachedCurrenciesLiveData.removeObserver(cachedCurrenciesObserver)
        super.onCleared()
    }
    //endregion

    //region get and store the conversion rates
    fun getConversionRates(delayInMs: Long = 0L) {
        ratesDataUseCase.getConversionRates(delayInMs, baseCurrencyPrivate.value ?: CurrencyEnum.EUR)
    }

    fun stopGettingConversionRates() {
        ratesDataUseCase.stopGetConversionRatesJob()
    }

    private fun storeConversionRates(ratesModel: Resource.Success<RatesModel>) {
        ratesDataUseCase.storeConversionRates(ratesModel)
    }
    //endregion

    //region base currency and multiplier
    // TODO: Call this on init
    fun setBaseCurrency(currencyEnum: CurrencyEnum) {
        setBaseCurrencyPrivate.value = Event(currencyEnum)
        baseCurrencyPrivate.value = currencyEnum

        ratesDataUseCase.setBaseCurrency(currencyEnum)

        // Get the new conversion rates for this new base currency
        stopGettingConversionRates()
        getConversionRates(250L)
    }

    fun setBaseMultiplier(multiplier: BigDecimal) {
        baseMultiplierPrivate.value = multiplier
    }
    //endregion

    //region Snackbar & error events
    fun showSnackbarMessage(@StringRes message: Int) {
        showSnackBarPrivate.value = Event(message)
    }

    fun showToastMessage(errorCode: Int) {
        val error = errorManager.getError(errorCode)
        showToastPrivate.value = Event(error.description)
    }
    //endregion

    fun onSearchClick(newsTitle: String) {
        if (newsTitle.isNotEmpty()) {
            // TODO: Do something else
            val RateItem = null // ratesDataUseCase.searchByTitle(newsTitle)
            if (RateItem != null) {
                newsSearchFoundPrivate.value = RateItem
            } else {
                noSearchFoundPrivate.postValue(Unit)
            }
        } else {
            noSearchFoundPrivate.postValue(Unit)
        }
    }
}
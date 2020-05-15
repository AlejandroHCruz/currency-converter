package com.alejandrohcruz.currency.ui.component.rates.activity

import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.alejandrohcruz.currency.data.Resource
import com.alejandrohcruz.currency.data.error.mapper.ErrorMapper
import com.alejandrohcruz.currency.data.remote.dto.RatesModel
import com.alejandrohcruz.currency.data.remote.dto.RatesItem
import com.alejandrohcruz.currency.model.CurrencyEnum
import com.alejandrohcruz.currency.ui.base.BaseViewModel
import com.alejandrohcruz.currency.usecase.RatesUseCase
import com.alejandrohcruz.currency.usecase.errors.ErrorManager
import com.alejandrohcruz.currency.utils.Event
import javax.inject.Inject

class RatesViewModel@Inject
constructor(private val ratesDataUseCase: RatesUseCase) : BaseViewModel() {

    override val errorManager: ErrorManager
        get() = ErrorManager(ErrorMapper())

    /**
     * Data --> LiveData, Exposed as LiveData, Locally in viewModel as MutableLiveData
     */
    var ratesLiveData: MutableLiveData<Resource<RatesModel>> = ratesDataUseCase.ratesLiveData

    private val newsSearchFoundPrivate: MutableLiveData<RatesItem> = MutableLiveData()
    val newsSearchFound: LiveData<RatesItem> get() = newsSearchFoundPrivate

    private val noSearchFoundPrivate: MutableLiveData<Unit> = MutableLiveData()
    val noSearchFound: LiveData<Unit> get() = noSearchFoundPrivate

    /**
     * UI actions as event, user action is single one time event, Shouldn't be multiple time consumption
     */
    private val setBaseCurrencyPrivate = MutableLiveData<Event<RatesItem>>()
    val setBaseCurrency: LiveData<Event<RatesItem>> get() = setBaseCurrencyPrivate

    /**
     * Error handling as UI
     */
    private val showSnackBarPrivate = MutableLiveData<Event<Int>>()
    val showSnackBar: LiveData<Event<Int>> get() = showSnackBarPrivate

    private val showToastPrivate = MutableLiveData<Event<Any>>()
    val showToast: LiveData<Event<Any>> get() = showToastPrivate


    fun getConversionRates(delayInMs: Long = 0L) {
        ratesDataUseCase.getConversionRates(delayInMs)
    }

    fun stopGettingConversionRates() {
        ratesDataUseCase.stopGetConversionRatesJob()
    }

    fun setBaseCurrency(currency: CurrencyEnum) {
        // TODO: Implement
        // setBaseCurrencyPrivate.value = Event(currency)
    }

    fun showSnackbarMessage(@StringRes message: Int) {
        showSnackBarPrivate.value = Event(message)
    }

    fun showToastMessage(errorCode: Int) {
        val error = errorManager.getError(errorCode)
        showToastPrivate.value = Event(error.description)
    }

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
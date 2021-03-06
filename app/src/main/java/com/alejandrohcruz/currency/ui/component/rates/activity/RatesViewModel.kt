package com.alejandrohcruz.currency.ui.component.rates.activity

import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.alejandrohcruz.currency.data.Resource
import com.alejandrohcruz.currency.data.error.Error.Companion.NO_ERROR
import com.alejandrohcruz.currency.data.error.mapper.ErrorMapper
import com.alejandrohcruz.currency.data.remote.dto.RatesModel
import com.alejandrohcruz.currency.data.remote.dto.RatesItem
import com.alejandrohcruz.currency.model.BaseMultiplier
import com.alejandrohcruz.currency.model.Currency
import com.alejandrohcruz.currency.model.CurrencyEnum
import com.alejandrohcruz.currency.model.ViewStateEnum
import com.alejandrohcruz.currency.ui.base.BaseViewModel
import com.alejandrohcruz.currency.usecase.RatesUseCase
import com.alejandrohcruz.currency.usecase.errors.ErrorManager
import com.alejandrohcruz.currency.utils.Constants.INSTANCE.DATA_REFRESH_DELAY
import com.alejandrohcruz.currency.utils.Event
import com.alejandrohcruz.currency.utils.L
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

    // Possible states are loading, content and error
    private val viewStateLiveDataPrivate: MutableLiveData<ViewStateEnum> = MutableLiveData()
    val viewStateLiveData: LiveData<ViewStateEnum> = viewStateLiveDataPrivate

    // Values as received from the network operation
    var remoteRatesLiveData: MutableLiveData<Resource<RatesModel>> = ratesDataUseCase.remoteRatesLiveData
    // Values in the database
    private val cachedCurrenciesLiveDataPrivate = ratesDataUseCase.cachedCurrenciesLiveData
    // Modified immediately on reordering the UI and/or when the values are updated on the database
    private val volatileCurrenciesLiveDataPrivate: MutableLiveData<List<Currency>> = MutableLiveData()
    // Used in the UI
    val volatileCurrenciesLiveData: LiveData<List<Currency>> = volatileCurrenciesLiveDataPrivate

    private val cachedBaseMultiplierLiveDataPrivate = ratesDataUseCase.cachedBaseMultiplierLiveData
    private val volatileBaseMultiplierLiveDataPrivate: MutableLiveData<Double> = MutableLiveData()
    val volatileBaseMultiplierLiveData: LiveData<Double> get() = volatileBaseMultiplierLiveDataPrivate

    private val baseCurrencyPrivate = MutableLiveData<CurrencyEnum>()

    /**
     * UI actions as event, user action is single one time event, Shouldn't be multiple time consumption
     */

    /**
     * Error handling in the UI, specifically for network requests
     */
    private val showRemoteRatesSnackBarPrivate = MutableLiveData<Event<Int>>()
    val showRemoteRatesSnackBar: LiveData<Event<Int>> get() = showRemoteRatesSnackBarPrivate

    //endregion

    //region Observer properties
    /**
     * Store the new rates when they change, notify the view if it should change
     */
    private val remoteRatesObserver = Observer<Resource<RatesModel>> {
        when (it) {
            is Resource.Success -> {
                // Save
                storeConversionRates(it)
                // No error, to free the snackbar to be used later if needed
                showRemoteRatesSnackbarMessage(NO_ERROR)
                // Update view if needed
                if (viewStateLiveDataPrivate.value != ViewStateEnum.CONTENT) {
                    viewStateLiveDataPrivate.value = ViewStateEnum.CONTENT
                }
            }
            is Resource.DataError -> {
                // Update view if needed
                if (viewStateLiveDataPrivate.value == ViewStateEnum.LOADING) {
                    viewStateLiveDataPrivate.value = ViewStateEnum.ERROR
                }
            }
        }
    }

    private val cachedCurrenciesObserver = Observer<List<Currency>> {
        L.i(TAG, "Data was successfully saved: $it")

        val baseCurrencyWasNotSet = baseCurrencyPrivate.value == null

        baseCurrencyPrivate.value = if (it?.isNotEmpty() == true) {
            if (volatileCurrenciesLiveDataPrivate.value != it) {
                volatileCurrenciesLiveDataPrivate.value = it
            }
            // Update view if needed
            if (viewStateLiveDataPrivate.value != ViewStateEnum.CONTENT) {
                viewStateLiveDataPrivate.value = ViewStateEnum.CONTENT
            }
            CurrencyEnum.valueOf(it.first().title)
        } else {
            CurrencyEnum.EUR
        }

        // Can only request the conversion rates with a valid base currency, let's do that now!
        if (baseCurrencyWasNotSet) getConversionRates()
    }

    private val cachedBaseMultiplierObserver = Observer<BaseMultiplier?> {
        it?.value?.apply {
            volatileBaseMultiplierLiveDataPrivate.value = this
        }
        L.i(TAG, "Base multiplier was successfully saved: $it")
    }
    //endregion

    //region lifecycle
    init {
        viewStateLiveDataPrivate.value = ViewStateEnum.LOADING
        remoteRatesLiveData.observeForever(remoteRatesObserver)
        cachedCurrenciesLiveDataPrivate.observeForever(cachedCurrenciesObserver)
        cachedBaseMultiplierLiveDataPrivate.observeForever(cachedBaseMultiplierObserver)
    }

    /**
     * This method will be called when this ViewModel is no longer used and will be destroyed.
     *
     *
     * It is useful when ViewModel observes some data and you need to clear this subscription to
     * prevent a leak of this ViewModel.
     */
    override fun onCleared() {
        remoteRatesLiveData.removeObserver(remoteRatesObserver)
        cachedCurrenciesLiveDataPrivate.removeObserver(cachedCurrenciesObserver)
        cachedBaseMultiplierLiveDataPrivate.removeObserver(cachedBaseMultiplierObserver)
        super.onCleared()
    }
    //endregion

    //region get and store the conversion rates
    fun getConversionRates(delayInMs: Long = 0L) {
        // Can only get conversion rates with a valid base currency, which comes from the local
        // database or defaults to Euro
        baseCurrencyPrivate.value?.let {
            ratesDataUseCase.getConversionRates(
                delayInMs,
                it
            )
        }
    }

    fun stopGettingConversionRates() {
        ratesDataUseCase.stopGetConversionRatesJob()
    }

    private fun storeConversionRates(ratesModel: Resource.Success<RatesModel>) {
        ratesDataUseCase.storeConversionRates(ratesModel)
    }
    //endregion

    //region base currency and multiplier
    fun setBaseCurrency(currencyEnum: CurrencyEnum, newBaseMultiplierValue: Double) {
        baseCurrencyPrivate.value = currencyEnum

        ratesDataUseCase.setBaseCurrency(currencyEnum)
        ratesDataUseCase.setBaseMultiplier(BaseMultiplier(newBaseMultiplierValue))

        volatileBaseMultiplierLiveDataPrivate.value = newBaseMultiplierValue

        // Get the new conversion rates for this new base currency
        stopGettingConversionRates()
        getConversionRates(DATA_REFRESH_DELAY.plus(500L))
    }

    fun setBaseMultiplierImmediately(newBaseMultiplierValue: Double) {
        if (newBaseMultiplierValue != volatileBaseMultiplierLiveDataPrivate.value) {
            volatileBaseMultiplierLiveDataPrivate.value = newBaseMultiplierValue
            ratesDataUseCase.setBaseMultiplier(BaseMultiplier(newBaseMultiplierValue))
        }
    }
    //endregion

    //region Snackbar & error events
    fun showRemoteRatesSnackbarMessage(@StringRes messageId: Int) {
        // Avoid showing the same error multiple times in a row
        if (showRemoteRatesSnackBarPrivate.value?.peekContent() != messageId) {
            showRemoteRatesSnackBarPrivate.value = Event(messageId)
        }
    }
    //endregion

    //region click throttling
    private var lastItemClickTimestamp = System.currentTimeMillis()

    /**
     * Throttle the click interaction to 300ms between click events to avoid potential
     * glitches and saving too often to the database.
     */
    fun shouldAllowItemToBeClicked(position: Int): Boolean {
        if (position == 0) return false

        val currentTimestamp = System.currentTimeMillis()
        return if (currentTimestamp.minus(lastItemClickTimestamp) > 300L) {
            lastItemClickTimestamp = currentTimestamp
            true
        } else false
    }
    //endregion
}
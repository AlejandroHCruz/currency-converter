package com.alejandrohcruz.currency.ui.base

import androidx.lifecycle.ViewModel
import com.alejandrohcruz.currency.usecase.errors.ErrorManager


abstract class BaseViewModel : ViewModel() {
    /**
     * Inject Singleton ErrorManager
     * Use this errorManager to get the Errors
     */
    abstract val errorManager: ErrorManager

}

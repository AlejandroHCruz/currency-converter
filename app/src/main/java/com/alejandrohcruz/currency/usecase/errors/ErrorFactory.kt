package com.alejandrohcruz.currency.usecase.errors

import com.alejandrohcruz.currency.data.error.Error

interface ErrorFactory {
    fun getError(errorCode: Int): Error
}
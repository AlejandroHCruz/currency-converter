package com.alejandrohcruz.currency.ui.base.listeners

import com.alejandrohcruz.currency.data.error.Error

interface BaseCallback {
    fun onSuccess(data: Any)

    fun onFail(error : Error)
}

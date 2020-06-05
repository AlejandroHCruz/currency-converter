package com.alejandrohcruz.currency.data.error

import com.alejandrohcruz.currency.R

class Error(val code: Int, val description: String) {
    constructor(exception: Exception) : this(code = DEFAULT_ERROR, description = exception.message
            ?: "")

    companion object {
        const val NO_INTERNET_CONNECTION = R.string.no_internet
        const val NETWORK_ERROR = R.string.network_error
        const val NO_ERROR = R.string.empty_string
        const val DEFAULT_ERROR = R.string.empty_string
    }
}
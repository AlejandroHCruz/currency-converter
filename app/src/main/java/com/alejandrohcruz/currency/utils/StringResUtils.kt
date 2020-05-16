package com.alejandrohcruz.currency.utils

import androidx.annotation.StringRes
import com.alejandrohcruz.currency.R
import com.alejandrohcruz.currency.model.CurrencyEnum

object StringResUtils {

    @StringRes
    fun getStringResForCurrency(currency: CurrencyEnum): Int {
        return when (currency) {
            CurrencyEnum.UNKNOWN -> R.string.UNKNOWN
            CurrencyEnum.AUD -> R.string.AUD
            CurrencyEnum.BGN -> R.string.BGN
            CurrencyEnum.BRL -> R.string.BRL
            CurrencyEnum.CAD -> R.string.CAD
            CurrencyEnum.CHF -> R.string.CHF
            CurrencyEnum.CNY -> R.string.CNY
            CurrencyEnum.CZK -> R.string.CZK
            CurrencyEnum.DKK -> R.string.DKK
            CurrencyEnum.EUR -> R.string.EUR
            CurrencyEnum.GBP -> R.string.GBP
            CurrencyEnum.HKD -> R.string.HKD
            CurrencyEnum.HRK -> R.string.HRK
            CurrencyEnum.HUF -> R.string.HUF
            CurrencyEnum.IDR -> R.string.IDR
            CurrencyEnum.ILS -> R.string.ILS
            CurrencyEnum.INR -> R.string.INR
            CurrencyEnum.ISK -> R.string.ISK
            CurrencyEnum.JPY -> R.string.JPY
            CurrencyEnum.KRW -> R.string.KRW
            CurrencyEnum.MXN -> R.string.MXN
            CurrencyEnum.MYR -> R.string.MYR
            CurrencyEnum.NOK -> R.string.NOK
            CurrencyEnum.NZD -> R.string.NZD
            CurrencyEnum.PHP -> R.string.PHP
            CurrencyEnum.PLN -> R.string.PLN
            CurrencyEnum.RON -> R.string.RON
            CurrencyEnum.RUB -> R.string.RUB
            CurrencyEnum.SEK -> R.string.SEK
            CurrencyEnum.SGD -> R.string.SGD
            CurrencyEnum.THB -> R.string.THB
            CurrencyEnum.USD -> R.string.USD
            CurrencyEnum.ZAR -> R.string.ZAR
        }
    }
}
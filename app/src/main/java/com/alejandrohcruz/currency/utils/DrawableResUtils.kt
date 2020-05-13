package com.alejandrohcruz.currency.utils

import androidx.annotation.DrawableRes
import com.alejandrohcruz.currency.R
import com.alejandrohcruz.currency.model.CurrencyEnum

object DrawableResUtils {

    @DrawableRes
    fun getDrawableResForCurrency(currency: CurrencyEnum): Int {

        return when (currency) {
            CurrencyEnum.UNKNOWN -> R.drawable.ic_unknown_currency_40dp
            CurrencyEnum.AUD -> R.drawable.ic_australia
            CurrencyEnum.BGN -> R.drawable.ic_bulgaria
            CurrencyEnum.BRL -> R.drawable.ic_brazil
            CurrencyEnum.CAD -> R.drawable.ic_canada
            CurrencyEnum.CHF -> R.drawable.ic_switzerland
            CurrencyEnum.CNY -> R.drawable.ic_china
            CurrencyEnum.CZK -> R.drawable.ic_czech_republic
            CurrencyEnum.DKK -> R.drawable.ic_denmark
            CurrencyEnum.GBP -> R.drawable.ic_united_kingdom
            CurrencyEnum.HKD -> R.drawable.ic_hong_kong
            CurrencyEnum.HRK -> R.drawable.ic_croatia
            CurrencyEnum.HUF -> R.drawable.ic_hungary
            CurrencyEnum.IDR -> R.drawable.ic_indonesia
            CurrencyEnum.ILS -> R.drawable.ic_israel
            CurrencyEnum.INR -> R.drawable.ic_india
            CurrencyEnum.ISK -> R.drawable.ic_iceland
            CurrencyEnum.JPY -> R.drawable.ic_japan
            CurrencyEnum.KRW -> R.drawable.ic_south_korea
            CurrencyEnum.MXN -> R.drawable.ic_mexico
            CurrencyEnum.MYR -> R.drawable.ic_malaysia
            CurrencyEnum.NOK -> R.drawable.ic_norway
            CurrencyEnum.NZD -> R.drawable.ic_new_zealand
            CurrencyEnum.PHP -> R.drawable.ic_philipinnes
            CurrencyEnum.PLN -> R.drawable.ic_poland
            CurrencyEnum.RON -> R.drawable.ic_romania
            CurrencyEnum.RUB -> R.drawable.ic_russia
            CurrencyEnum.SEK -> R.drawable.ic_sweden
            CurrencyEnum.SGD -> R.drawable.ic_singapore
            CurrencyEnum.THB -> R.drawable.ic_thailand
            CurrencyEnum.USD -> R.drawable.ic_united_states
            CurrencyEnum.ZAR -> R.drawable.ic_south_africa
        }
    }
}
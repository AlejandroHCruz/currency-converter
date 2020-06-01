package com.alejandrohcruz.currency.utils

import java.lang.NumberFormatException
import java.math.RoundingMode

/**
 * @brief Trims out the decimal values for natural numbers and 00 for decimal numbers
 */
fun Double.toPresentableString(): String {
    return try {
        this.toBigDecimal().setScale(2, RoundingMode.HALF_UP) // round
            .toPlainString()
            .removeSuffix(".00")
            .removeSuffix(".0")
    } catch (e: NumberFormatException) {
        this.toString()
    }
}
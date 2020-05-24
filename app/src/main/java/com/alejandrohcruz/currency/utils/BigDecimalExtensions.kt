package com.alejandrohcruz.currency.utils

import java.math.BigDecimal
import java.math.RoundingMode

/**
 * @brief Trims out the decimal values for natural numbers and 00 for decimal numbers
 */
fun BigDecimal.toPresentableString(): String {
    return this.setScale(2, RoundingMode.HALF_UP) // round
        .toPlainString()
        .removeSuffix(".00")
        .removeSuffix(".0")
}
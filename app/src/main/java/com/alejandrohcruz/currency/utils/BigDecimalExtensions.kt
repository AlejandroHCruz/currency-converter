package com.alejandrohcruz.currency.utils

import java.math.BigDecimal

/**
 * @brief Trims out the decimal values for natural numbers and 00 for decimal numbers
 */
fun BigDecimal.toPresentableString(): String {
    this.toPlainString().apply {
        return if (endsWithDotAndZeros()) {
            // Round
            this@toPresentableString.toInt().toBigDecimal().toPlainString()
        } else if (containsADot() && endsWith("00")) {
            removeSuffix("00")
        } else if (containsADot() && endsWithUnnecessaryDecimalZero()) {
            removeSuffix("0")
        } else {
            this
        }
    }
}
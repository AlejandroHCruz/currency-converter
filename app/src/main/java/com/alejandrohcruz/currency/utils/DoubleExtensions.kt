package com.alejandrohcruz.currency.utils

/**
 * @brief Trims out the decimal values for natural numbers
 */
fun Double.toPresentableString(): String {
    val doubleAsString = this.toString()
    return if (doubleAsString.endsWith(".0") || doubleAsString.endsWith(".00")) {
        this.toInt().toString()
    } else doubleAsString
}
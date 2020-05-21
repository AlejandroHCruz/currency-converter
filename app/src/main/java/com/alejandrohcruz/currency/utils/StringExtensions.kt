package com.alejandrohcruz.currency.utils

/**
 * Checks up to three zeros, to accommodate working with BigDecimal
 */
fun String.endsWithDotAndZeros(): Boolean {
    return endsWith(".0") || endsWith(".00") || endsWith(".000") || endsWith(".0000")
}

fun String.containsADot(): Boolean {
    return contains('.')
}

fun String.endsWithUnnecessaryDecimalZero(): Boolean {
    return this.containsADot() && (indexOf('.') == length.minus(4)
            && endsWith('0'))
}
package com.alejandrohcruz.currency.model

/**
 * Defines all the known currency types
 */
enum class CurrencyEnum {
    UNKNOWN,
    AUD, // Australia Dollar
    BGN, // Bulgaria Lev
    BRL, // Brazil Real
    CAD, // Canada Dollar
    CHF, // Switzerland Franc
    CNY, // China Yuan/Renminbi
    CZK, // Czech Koruna
    DKK, // Denmark Krone
    EUR, // Euro
    GBP, // Great Britain Pound
    HKD, // Hong Kong Dollar
    HRK, // Croatia Kuna
    HUF, // Hungary Forint
    IDR, // Indonesia Rupiah
    ILS, // Israel New Shekel
    INR, // India Rupee
    ISK, // Iceland Krona
    JPY, // Japan Yen
    KRW, // South Korea Won
    MXN, // Mexico Peso
    MYR, // Malaysia Ringgit
    NOK, // Norway Kroner
    NZD, // New Zealand Dollar
    PHP, // Philippines Peso
    PLN, // Poland Zloty
    RON, // Romania New Lei
    RUB, // Russia Rouble
    SEK, // Sweden Krona
    SGD, // Singapore Dollar
    THB, // Thailand Baht
    USD, // USA Dollar
    ZAR; // South Africa Rand

    companion object {

        // Keep in memory, avoid re-creating a new array every time fromString() is called
        private val values = values()

        fun fromString(enumAsString: String): CurrencyEnum {
            return values.find { it.name == enumAsString } ?: UNKNOWN
        }
    }
}
package com.alejandrohcruz.currency.utils

class Constants {
    companion object INSTANCE {
        const val DATA_REFRESH_DELAY = 1_000L
        const val BASE_URL = "https://hiring.revolut.codes/"
        const val ANDROID_API_URL = "api/android/"
        //endregion

        //region local repository
        const val DATABASE_NAME = "currency_database"
        const val DATABASE_VERSION = 1
        const val TABLE_CURRENCY = "currency_table"
        const val COLUMN_TITLE = "title"
        const val COLUMN_RATE = "exchange_rate"
        const val COLUMN_POSITION = "position"
        //endregion
    }
}
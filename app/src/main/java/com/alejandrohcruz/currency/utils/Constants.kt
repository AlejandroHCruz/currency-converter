package com.alejandrohcruz.currency.utils

class Constants {
    companion object INSTANCE {

        //region network constants
        const val TIMEOUT_CONNECT = 30L   // In seconds
        const val TIMEOUT_READ = 30L      // In seconds
        const val CONTENT_TYPE = "Content-Type"
        const val CONTENT_TYPE_VALUE = "application/json"
        //endregion

        //region remote repository
        const val DATA_REFRESH_DELAY = 1_000L
        const val BASE_URL = "https://hiring.revolut.codes/"
        const val ANDROID_API_URL = "api/android/"
        //endregion

        //region local repository
        const val DATABASE_NAME = "currency_database"
        const val DATABASE_VERSION = 1
        const val TABLE_BASE_MULTIPLIER = "base_multiplier_table"
        const val TABLE_CURRENCY = "currency_table"
        const val COLUMN_TITLE = "title"
        const val COLUMN_BASE_MULTIPLIER_ID = "id"
        const val COLUMN_BASE_MULTIPLIER_VALUE = "value"
        const val COLUMN_RATE = "exchange_rate"
        const val COLUMN_POSITION = "position"

        const val BASE_MULTIPLIER_UNIQUE_ID = 1
        //endregion
    }
}
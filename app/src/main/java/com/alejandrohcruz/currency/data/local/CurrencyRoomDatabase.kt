package com.alejandrohcruz.currency.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.alejandrohcruz.currency.model.BaseMultiplier
import com.alejandrohcruz.currency.utils.Constants.INSTANCE.DATABASE_VERSION
import com.alejandrohcruz.currency.model.Currency

/**
 * Room Database with a single table (entity) of the Currency class
 */
@Database(entities = [Currency::class, BaseMultiplier::class], version = DATABASE_VERSION)
abstract class CurrencyRoomDatabase : RoomDatabase() {
    abstract fun baseMultiplierDao(): BaseMultiplierDao
    abstract fun currencyDao(): CurrencyDao
}
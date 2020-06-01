package com.alejandrohcruz.currency.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.alejandrohcruz.currency.utils.Constants.INSTANCE.COLUMN_POSITION
import com.alejandrohcruz.currency.model.Currency
import com.alejandrohcruz.currency.utils.Constants.INSTANCE.TABLE_CURRENCY

/**
 * Data Access Object for querying the local SQLite currency database
 */
@Dao
interface CurrencyDao {

    @Query("SELECT * from currency_table ORDER BY $COLUMN_POSITION ASC")
    fun getOrderedCurrenciesLiveData(): LiveData<List<Currency>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrReplace(currencies: List<Currency>)

    @Update
    suspend fun updateCurrencies(currencies: List<Currency>)
}
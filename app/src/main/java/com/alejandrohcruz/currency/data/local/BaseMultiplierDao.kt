package com.alejandrohcruz.currency.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.alejandrohcruz.currency.model.BaseMultiplier
import com.alejandrohcruz.currency.utils.Constants.INSTANCE.COLUMN_POSITION
import com.alejandrohcruz.currency.model.Currency
import com.alejandrohcruz.currency.utils.Constants.INSTANCE.TABLE_CURRENCY

/**
 * Data Access Object for querying the local SQLite currency database
 */
@Dao
interface BaseMultiplierDao {

    @Query("SELECT * from base_multiplier_table LIMIT 1")
    fun getUniqueBaseMultiplier(): LiveData<BaseMultiplier?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrReplace(baseMultiplier: BaseMultiplier)
}
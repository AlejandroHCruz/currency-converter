package com.alejandrohcruz.currency.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.alejandrohcruz.currency.utils.Constants.INSTANCE.BASE_MULTIPLIER_UNIQUE_ID
import com.alejandrohcruz.currency.utils.Constants.INSTANCE.COLUMN_BASE_MULTIPLIER_ID
import com.alejandrohcruz.currency.utils.Constants.INSTANCE.COLUMN_BASE_MULTIPLIER_VALUE
import com.alejandrohcruz.currency.utils.Constants.INSTANCE.TABLE_BASE_MULTIPLIER

/**
 * Represents the input of the user, in the base currency.
 * This table should only contain one value.
 */
@Entity(tableName = TABLE_BASE_MULTIPLIER)
data class BaseMultiplier(
    @ColumnInfo(name = COLUMN_BASE_MULTIPLIER_VALUE)
    val value: Double,
    @PrimaryKey @ColumnInfo(name = COLUMN_BASE_MULTIPLIER_ID)
    val id: Int? = BASE_MULTIPLIER_UNIQUE_ID
)
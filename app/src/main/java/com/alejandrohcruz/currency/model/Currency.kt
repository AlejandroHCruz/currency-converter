package com.alejandrohcruz.currency.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.alejandrohcruz.currency.utils.Constants.INSTANCE.COLUMN_POSITION
import com.alejandrohcruz.currency.utils.Constants.INSTANCE.COLUMN_RATE
import com.alejandrohcruz.currency.utils.Constants.INSTANCE.COLUMN_TITLE
import com.alejandrohcruz.currency.utils.Constants.INSTANCE.TABLE_CURRENCY

/**
 * Model of a currency, with all the data that has to be persisted for later use.
 *
 * @param title: The unique, more technical, abbreviated name of the currency. Untranslatable.
 * @param rate: The exchange rate in respect of the base currency.
 * @param position: Preferred position in the list, as per the first time they're loaded
 *                  and can be later modified by the user clicking on the item.
 *                  Note that the first one (position 0) will be the base currency.
 */
@Entity(tableName = TABLE_CURRENCY)
data class Currency(
    @PrimaryKey @ColumnInfo(name = COLUMN_TITLE) val title: String,
    @ColumnInfo(name = COLUMN_RATE) val rate: Double,
    @ColumnInfo(name = COLUMN_POSITION) val position: Int
)

package com.alejandrohcruz.currency.di

import androidx.room.Room
import com.alejandrohcruz.currency.App
import com.alejandrohcruz.currency.data.DataRepository
import com.alejandrohcruz.currency.data.DataSource
import com.alejandrohcruz.currency.data.local.BaseMultiplierDao
import com.alejandrohcruz.currency.data.local.CurrencyDao
import com.alejandrohcruz.currency.data.local.CurrencyRoomDatabase
import com.alejandrohcruz.currency.data.local.LocalRepository
import com.alejandrohcruz.currency.utils.Constants
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


// Tells Dagger this is a Dagger module
@Module
class DataModule {

    private val database: CurrencyRoomDatabase = Room.databaseBuilder(
        App.context,
        CurrencyRoomDatabase::class.java,
        Constants.DATABASE_NAME
    ).build()

    @Provides
    @Singleton
    fun provideDataRepository(dataRepository: DataRepository): DataSource {
        return dataRepository
    }

    @Provides
    @Singleton
    fun providesRoomDatabase(): CurrencyRoomDatabase {
        return database
    }

    @Provides
    @Singleton
    fun providesBaseMultiplierDao(demoDatabase: CurrencyRoomDatabase): BaseMultiplierDao {
        return demoDatabase.baseMultiplierDao()
    }

    @Provides
    @Singleton
    fun providesCurrencyDao(demoDatabase: CurrencyRoomDatabase): CurrencyDao {
        return demoDatabase.currencyDao()
    }

    @Provides
    @Singleton
    fun provideLocalRepository(currencyDao: CurrencyDao, baseMultiplierDao: BaseMultiplierDao): LocalRepository {
        return LocalRepository(currencyDao, baseMultiplierDao)
    }
}

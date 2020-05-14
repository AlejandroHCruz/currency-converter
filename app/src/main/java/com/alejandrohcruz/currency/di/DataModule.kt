
package com.alejandrohcruz.currency.di

import com.alejandrohcruz.currency.data.DataRepository
import com.alejandrohcruz.currency.data.DataSource
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

// Tells Dagger this is a Dagger module
@Module
abstract class DataModule {
    @Binds
    @Singleton
    abstract fun provideDataRepository(dataRepository: DataRepository): DataSource
}

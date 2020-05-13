package com.alejandrohcruz.currency

import android.app.Application
import android.content.Context
import com.alejandrohcruz.currency.di.DaggerAppComponent
import com.alejandrohcruz.currency.utils.SslUtils
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class App : Application(), HasAndroidInjector {

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    override fun androidInjector(): AndroidInjector<Any> = androidInjector

    override fun onCreate() {
        super.onCreate()

        context = applicationContext
        initDagger()

        // Make sure all internet connection is performed through a modern SSL Engine
        SslUtils.initSslEngine()
        SslUtils.updateAndroidSecurityProviderIfNeeded(applicationContext)
    }

    open fun initDagger() {
        DaggerAppComponent.builder().build().inject(this)
    }

    companion object {
        lateinit var context: Context

    }
}
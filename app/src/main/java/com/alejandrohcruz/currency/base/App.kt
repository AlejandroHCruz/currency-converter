package com.alejandrohcruz.currency.base

import android.app.Application
import com.alejandrohcruz.currency.utils.SslUtils

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        // Make sure all internet connection is performed through a modern SSL Engine
        SslUtils.initSslEngine()
        SslUtils.updateAndroidSecurityProviderIfNeeded(applicationContext)
    }
}
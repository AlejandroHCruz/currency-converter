package com.alejandrohcruz.currency.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.alejandrohcruz.currency.R
import com.alejandrohcruz.currency.ui.currencyconverter.CurrencyConverterFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, CurrencyConverterFragment.newInstance())
                    .commitNow()
        }
    }
}
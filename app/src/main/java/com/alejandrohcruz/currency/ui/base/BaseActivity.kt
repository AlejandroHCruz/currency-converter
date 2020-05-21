package com.alejandrohcruz.currency.ui.base

import android.graphics.Color
import android.os.Bundle
import android.view.MenuItem
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.alejandrohcruz.currency.R
import com.alejandrohcruz.currency.ui.base.listeners.BaseView
import dagger.android.AndroidInjection


abstract class BaseActivity : AppCompatActivity(), BaseView {

    protected abstract fun initializeViewModel()
    abstract fun observeViewModel()
    protected abstract fun initViewBinding()

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        initViewBinding()
        initializeViewModel()
        observeViewModel()
        //region set soft buttons to themed gray
        window.apply {
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            navigationBarColor = ContextCompat.getColor(this@BaseActivity, R.color.colorPrimaryDark)
        }
        //endregion
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }
}

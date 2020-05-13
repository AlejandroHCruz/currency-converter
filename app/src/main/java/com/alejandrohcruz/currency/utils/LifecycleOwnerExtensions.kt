package com.alejandrohcruz.currency.utils

import androidx.lifecycle.*

fun <T> LifecycleOwner.observe(liveData: LiveData<T>, action: (t: T) -> Unit) {
    liveData.observe(this, Observer { it?.let { t -> action(t) } })
}

fun <T> LifecycleOwner.observeEvent(liveData: LiveData<Event<T>>, action: (t: Event<T>) -> Unit) {
    liveData.observe(this, Observer { it?.let { t -> action(t) } })
}
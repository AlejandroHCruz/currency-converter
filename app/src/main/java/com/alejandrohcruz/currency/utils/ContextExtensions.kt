package com.alejandrohcruz.currency.utils

import android.content.Context
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability

/**
 * Check the device to make sure it has the Google Play Services APK.
 */
fun Context.checkPlayServicesAvailability(): Boolean {

    val googleApiAvailability = GoogleApiAvailability.getInstance()

    val resultCode = googleApiAvailability.isGooglePlayServicesAvailable(this)

    if (resultCode != ConnectionResult.SUCCESS) {
        if (googleApiAvailability.isUserResolvableError(resultCode)) {
            // TODO: Display a toast or error dialog
        }
    }
    return resultCode == ConnectionResult.SUCCESS
}
package com.alejandrohcruz.currency.utils

import android.Manifest
import android.content.Context
import android.net.ConnectivityManager
import android.widget.Toast
import androidx.annotation.RequiresPermission
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability

/**
 * @brief Check the device to make sure it has the Google Play Services APK.
 */
fun Context.checkPlayServicesAvailability(): Boolean {

    val googleApiAvailability = GoogleApiAvailability.getInstance()

    val resultCode = googleApiAvailability.isGooglePlayServicesAvailable(this)

    if (resultCode != ConnectionResult.SUCCESS) {
        if (googleApiAvailability.isUserResolvableError(resultCode)) {
            L.e("checkPlayServicesAvailability", "User solvable error: $resultCode")
        }
    }
    return resultCode == ConnectionResult.SUCCESS
}

/**
 * @brief Check if the device is currently connected to the internet
 * NOTE: It does not account for the internet's bandwidth, always keep 2G (metro!) in mind
 */
@RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
fun Context?.isConnected(): Boolean {
    val cm = this?.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
    val info = cm?.activeNetworkInfo
    return info?.isConnected ?: false
}
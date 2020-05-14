package com.alejandrohcruz.currency.utils

import android.content.Context
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.common.GooglePlayServicesNotAvailableException
import com.google.android.gms.common.GooglePlayServicesRepairableException
import com.google.android.gms.security.ProviderInstaller
import okhttp3.TlsVersion
import java.security.KeyManagementException
import java.security.NoSuchAlgorithmException
import javax.net.ssl.SSLContext

object SslUtils {

    /**
     * @brief Make sure that the SSL Engine has the correct configuration
     * Source: https://github.com/parse-community/Parse-SDK-Android/issues/448#issuecomment-300308099
     */
    fun initSslEngine() {
        val sslContext: SSLContext? = try {
            // Use the newest TLS, added on Android 10.0
            SSLContext.getInstance(TlsVersion.TLS_1_3.toString())
        } catch (e: NoSuchAlgorithmException) {
            try {
                // Use the second newest TLS, which should be supported on most Android versions
                SSLContext.getInstance(TlsVersion.TLS_1_2.toString())
            } catch (e2: NoSuchAlgorithmException) {
                // No modern TLS is supported
                null
            }
        }

        sslContext?.let {
            val wasSslInitialized: Boolean = try {
                // Initialize the SSL Context
                it.init(null, null, null)
                true
            } catch (e: KeyManagementException) {
                // TODO: Handle
                false
            }
            if (wasSslInitialized) {
                // Create the SSL Engine
                it.createSSLEngine()
            }
        }
    }

    /**
     * @brief Adds support for TLSv1.2 and TLSv1.3
     *
     * This is useful for pre-Lollipop devices but more modern devices have anecdotically had this
     * issue too. So the check is performed every time the app
     * is opened, which makes the code and server connections future proof.
     *
     * Google: https://developer.android.com/training/articles/security-gms-provider
     * Source: https://stackoverflow.com/questions/29916962/javax-net-ssl-sslhandshakeexception-javax-net-ssl-sslprotocolexception-ssl-han
     *
     * ⚠️ Caution: Updating a device's security Provider does not update
     * android.net.SSLCertificateSocketFactory. Rather than using this class,
     * we encourage app developers to use high-level methods for interacting with cryptography.
     * Most apps can use APIs like HttpsURLConnection without needing to set a custom TrustManager
     * or create an SSLCertificateSocketFactory… That's why we setup the SSL version on
     * @link{#initSslEngine()}
     */
    fun updateAndroidSecurityProviderIfNeeded(context: Context?) {
        context?.let {
            try {
                ProviderInstaller.installIfNeeded(it)
            } catch (e: GooglePlayServicesRepairableException) {

                val isGooglePlayServicesAvailable = it.checkPlayServicesAvailability()

                if (isGooglePlayServicesAvailable) {
                    // Prompt the user to install/update/enable Google Play services,
                    // by showing a push notification on the device.
                    GoogleApiAvailability.getInstance()
                        .showErrorNotification(it, e.connectionStatusCode)
                }

            } catch (e: GooglePlayServicesNotAvailableException) {
                // TODO: Handle
            }
        }
    }
}
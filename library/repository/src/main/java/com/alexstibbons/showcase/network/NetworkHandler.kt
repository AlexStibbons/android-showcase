package com.alexstibbons.showcase.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.core.content.getSystemService
import com.alexstibbons.showcase.simpleName

interface NetworkHandler {
    val isConnected: Boolean
}

internal class NetworkHandlerImpl(
    private val context: Context
) : NetworkHandler {
    private val connectivityManager by lazy { context.getSystemService<ConnectivityManager>()!! }

    private val networkTransports = listOf(
        NetworkCapabilities.TRANSPORT_WIFI,
        NetworkCapabilities.TRANSPORT_CELLULAR,
        //for other device how are able to connect with Ethernet
        NetworkCapabilities.TRANSPORT_ETHERNET,
        //for check internet over Bluetooth
        NetworkCapabilities.TRANSPORT_BLUETOOTH
    )

    override val isConnected: Boolean
        get() {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val network = connectivityManager.activeNetwork ?: return false
                val networkCapabilities = connectivityManager.getNetworkCapabilities(network)
                    ?: return false

                return networkTransports.any { networkTransport ->
                    networkCapabilities.hasTransport(networkTransport)
                }
            } else {
                return connectivityManager.activeNetworkInfo?.isConnected ?: false
            }
        }

    companion object {
        private val TAG: String =
            simpleName<NetworkHandlerImpl>()
    }
}
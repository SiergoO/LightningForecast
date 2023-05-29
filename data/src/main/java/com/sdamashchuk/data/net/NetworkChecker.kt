package com.sdamashchuk.data.net

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

class NetworkChecker(private val context: Context) {

    val isConnected: Boolean
        @SuppressLint("MissingPermission")
        get() = (context.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager)?.run {
            getNetworkCapabilities(activeNetwork)?.run {
                when {
                    hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                    hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                    hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                    else -> false
                }
            } ?: false
        } ?: false
}

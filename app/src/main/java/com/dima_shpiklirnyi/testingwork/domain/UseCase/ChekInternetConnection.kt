package com.dima_shpiklirnyi.testingwork.domain.UseCase


import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import com.dima_shpiklirnyi.testingwork.domain.Interfaces.NoInternetFunc


class ChekInternetConnection(
    private val context: Context,
    private val noInternetFunc: NoInternetFunc
) {
    fun isOnline(): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val n = cm.activeNetwork
            if (n != null) {
                val nc = cm.getNetworkCapabilities(n)
                return nc!!.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) || nc.hasTransport(
                    NetworkCapabilities.TRANSPORT_WIFI
                )
            }
            return false
        } else {
            val netInfo = cm.activeNetworkInfo
            return netInfo != null && netInfo.isConnectedOrConnecting
        }
    }

    fun isChangeInternet() {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            cm.registerDefaultNetworkCallback(object : ConnectivityManager.NetworkCallback() {
                override fun onLost(network: Network) {
                    super.onLost(network)
                    noInternetFunc.internet(false)
                }

                override fun onAvailable(network: Network) {
                    super.onAvailable(network)
                    noInternetFunc.internet(true)
                }
            })
        } else {
            NetworkRequest.Builder()
                .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
                .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
                .build().let { networkRequest ->
                    cm.registerNetworkCallback(
                        networkRequest,
                        object : ConnectivityManager.NetworkCallback() {

                            override fun onLost(network: Network) {
                                super.onLost(network)
                                noInternetFunc.internet(false)
                            }

                            override fun onAvailable(network: Network) {
                                super.onAvailable(network)
                                noInternetFunc.internet(true)
                            }

                        })
                }
        }

    }
}
package com.example.movieshub.utils

import android.content.Context
import android.net.ConnectivityManager

open class NetworkSystem(private val appContext: Context) {

    fun isNetworkAvailable(): Boolean {
        val connectivityManager = appContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting
    }
}
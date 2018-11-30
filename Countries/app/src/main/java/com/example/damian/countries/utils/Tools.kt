package com.example.damian.countries.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo


fun getArrayAsString(stringArray: Array<String>, separator: String): String {
    if (stringArray.isEmpty()) return ""

    val finalString = StringBuilder(stringArray[0])

    for (i in 1 until stringArray.size)
        finalString.append(separator).append(stringArray[i])

    return finalString.toString()
}

fun hasNetwork(context: Context): Boolean {
    var isConnected = false
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
    if (activeNetwork != null && activeNetwork.isConnected) {
        isConnected = true
    }
    return isConnected
}
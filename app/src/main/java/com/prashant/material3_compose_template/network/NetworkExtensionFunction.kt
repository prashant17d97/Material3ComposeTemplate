package com.prashant.material3_compose_template.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import com.google.gson.GsonBuilder
import com.google.gson.JsonElement
import com.prashant.material3_compose_template.activity.MainActivity

object NetworkExtensionFunction {
    inline fun <reified Generic> jsonElementToData(
        responseData: JsonElement?,
        response: (Generic) -> Unit
    ) {
        try {
            response(GsonBuilder().create().fromJson(responseData, Generic::class.java))
        } catch (e: java.lang.IllegalStateException) {
            Log.e("ContextExtension", "jsonStringToData Error: ${e.message}")
        }
    }

    fun showProgress() {
        (MainActivity.weakReference.get() as MainActivity).showLoader(boolean = true)
    }

    fun hideProgress() {
        (MainActivity.weakReference.get() as MainActivity).showLoader(boolean = false)
    }


    fun Context.isNetworkAvailable(): Boolean {
        val connectivityManager =
            this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.getNetworkCapabilities(
            connectivityManager.activeNetwork ?: return false
        ) ?: return false

        return when {
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }
}
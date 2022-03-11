package com.purnendu.PocketNews

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.recyclerview.widget.RecyclerView
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class Utility {


    companion object {
        fun checkConnection(context: Context): Boolean {
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val nw      = connectivityManager.activeNetwork ?: return false
                val actNw = connectivityManager.getNetworkCapabilities(nw) ?: return false
                return when {
                    actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                    actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                    //for other device how are able to connect with Ethernet
                    actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                    //for check internet over Bluetooth
                    actNw.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) -> true
                    else -> false
                }
            } else {
                return connectivityManager.activeNetworkInfo?.isConnected ?: false
            }
        }


        fun getProperDateInFormat(unformattedDate: String?): String {
            @SuppressLint("SimpleDateFormat") val format = SimpleDateFormat(
                "yyyy-MM-dd'T'HH:mm:ss'Z'"
            )
            if(unformattedDate==null)
                return ""
            format.timeZone = TimeZone.getTimeZone("UTC")
            var dateObj: Date? = null
            try {
                dateObj = format.parse(unformattedDate)
            } catch (ignored: ParseException) {
            }
            @SuppressLint("SimpleDateFormat") val postFormat = SimpleDateFormat("dd MMMM HH:mm")
            return if (dateObj == null) {
                "Date"
            } else postFormat.format(dateObj)
        }

        fun getNightModeState(context: Context): Boolean {
            val sharedPreferences: SharedPreferences =
                context.getSharedPreferences("switch", Context.MODE_PRIVATE)
            return sharedPreferences.getBoolean("nightMode", false)
        }

        fun getSelectedCountryCode(context: Context): String {
            val sharedPreferences: SharedPreferences = context.getSharedPreferences(
                "MyPrefs",
                Application.MODE_PRIVATE
            )
            return sharedPreferences.getString("country", "in")!!
        }

        fun getJavaScriptModeState(context: Context): Boolean {
            val sharedPreferences: SharedPreferences =
                context.getSharedPreferences("javaScriptSwitch", Context.MODE_PRIVATE)
            return sharedPreferences.getBoolean("js", false)
        }


         fun scrollToTop(recycler :RecyclerView) {
             // Call smooth scroll
            recycler.post {
                recycler.smoothScrollToPosition(0)
            }
         }


    }

}

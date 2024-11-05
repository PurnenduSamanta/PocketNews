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

        //Checking network is available or not
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

        //Converting to proper date format
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

        //Set night mode status
        fun setNightMode(context: Context,state: Boolean): Boolean {
            val editor: SharedPreferences.Editor =
                context.getSharedPreferences("switch", Context.MODE_PRIVATE).edit()
            editor.putBoolean("nightMode", state)
            return editor.commit()
        }

        //Set javascript status
        fun setJavaScriptStatus(context: Context,state: Boolean): Boolean {
            val editor: SharedPreferences.Editor =
                context.getSharedPreferences("javaScriptSwitch", Context.MODE_PRIVATE).edit()
            editor.putBoolean("js", state)
            return editor.commit()
        }

        //Set country code to show news of the particular country
         fun setCountryCode(context: Context,CountryCode: String): Boolean {
            val preference = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
            val editor: SharedPreferences.Editor = preference.edit()
            editor.putString("country", CountryCode)
            return editor.commit()
            }

        //Get night mode status
        fun getNightModeState(context: Context): Boolean {
            val sharedPreferences: SharedPreferences =
                context.getSharedPreferences("switch", Context.MODE_PRIVATE)
            return sharedPreferences.getBoolean("nightMode", false)
        }

        //Get Country code
        fun getSelectedCountryCode(context: Context): String {
            val sharedPreferences: SharedPreferences = context.getSharedPreferences(
                "MyPrefs",
                Application.MODE_PRIVATE
            )
            return sharedPreferences.getString("country", "us")!!
        }

        //Set JavaScript status
        fun getJavaScriptModeState(context: Context): Boolean {
            val sharedPreferences: SharedPreferences =
                context.getSharedPreferences("javaScriptSwitch", Context.MODE_PRIVATE)
            return sharedPreferences.getBoolean("js", false)
        }

        //Scroll to first position of RecyclerView
        fun scrollToTop(recycler :RecyclerView) {
             // Call smooth scroll
            recycler.post {
                recycler.smoothScrollToPosition(0)
            }
         }


    }

}

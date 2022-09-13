package com.example.myapplication.common_util

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.widget.ImageView
import com.example.myapplication.R
import dev.shreyaspatil.MaterialDialog.BottomSheetMaterialDialog
import dev.shreyaspatil.MaterialDialog.model.TextAlignment

object Utils {
    var IS_API_NOT_WORKING = false

    fun showNoInternetDialog(activity: Activity) {
        val mDialog = BottomSheetMaterialDialog.Builder(activity)
            .setTitle(activity.getString(R.string.title_no_internet), TextAlignment.START)
            .setMessage(activity.getString(R.string.msg_no_internet), TextAlignment.START)
            .setCancelable(true)
            .setAnimation("no_internet.json")
            .setPositiveButton(
                activity.getString(R.string.text_ok)
            ) { dialogInterface, which ->
                dialogInterface.dismiss()
            }
            .build()

        mDialog.show()

        val animationView = mDialog.animationView
        animationView.scaleType = ImageView.ScaleType.CENTER_INSIDE
    }

    fun isNetworkAvailable(activity: Activity) = isNetworkAvailable(activity, true)

    fun isNetworkAvailable(activity: Activity, showDialog: Boolean): Boolean {
        var isConnected = false
        // register activity with the connectivity manager service
        val connectivityManager =
            activity.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        // if the android version is equal to M
        // or greater we need to use the
        // NetworkCapabilities to check what type of
        // network has the internet connection
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            // Returns a Network object corresponding to
            // the currently active default data network.
            //val network = connectivityManager.activeNetwork ?: return false

            // Representation of the capabilities of an active network.
            val activeNetwork =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            activeNetwork?.let {
                isConnected = when {
                    // Indicates this network uses a Wi-Fi transport,
                    // or WiFi has network connectivity
                    activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true

                    // Indicates this network uses a Cellular transport. or
                    // Cellular has network connectivity
                    activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true

                    // else return false
                    else -> false

                }
            }
        } else {
            // if the android version is below M
            @Suppress("DEPRECATION")
            isConnected = connectivityManager.activeNetworkInfo?.isConnected ?: false

        }

        if (showDialog && !isConnected) {
            //DebugUtils.debug("no_internet", "no internet available")
            showNoInternetDialog(activity)
        } else {
            //DebugUtils.debug("no_internet", "internet available")
        }
        return isConnected
    }
}

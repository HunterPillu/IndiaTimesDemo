package com.example.myapplication.common_util

import android.util.Log

object DebugUtils {

    fun debug(TAG: String, msg: String?) {
        Log.d(TAG, "" + msg)
    }

    fun e(TAG: String, msg: String?) {
        Log.d(TAG, "" + msg)
    }

    fun w(TAG: String, msg1: String, msg: String?) {
        Log.w(TAG, msg1 + msg)
    }

}

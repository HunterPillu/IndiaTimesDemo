package com.example.myapplication.common_util

import android.content.Context
import androidx.preference.PreferenceManager
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

object PrefUtils {
    const val KEY_SESSION = "session"
    const val KEY_USERNAME = "username"
    const val KEY_PASSWORD = "password"


    const val KEY_PHONE = "phone"
    const val KEY_EMAIL = "email"
    const val KEY_REMEMBER_ME = "remember_me"
    const val KEY_EMAIL_CACHE = "email_cache"
    const val KEY_PHONE_CACHE = "phone_cache"
    const val KEY_COOKIE = "cookie"
    const val KEY_USER_ID = "userId"
    const val KEY_LOGGED_IN = "loggedin"
    const val KEY_NOTIFICATION_ALL = "n_all"
    const val KEY_NOTIFICATION_SOUND = "n_sound"
    const val KEY_NOTIFICATION_VIBRATE = "n_vibrate"
    const val KEY_SATELLITE = "map_satellite"
    const val KEY_MAP_ZOOM = "map_zoom"

    const val KEY_ASS_ID = "association_id"
    const val KEY_REQ_ID = "request_id"
    const val KEY_GP_TOKEN = "google_payment_token"

    fun getUserName(context: Context): String {
        return PreferenceManager.getDefaultSharedPreferences(context).getString(KEY_USERNAME, "")
            ?: ""
    }

    fun getPassword(context: Context): String {
        return PreferenceManager.getDefaultSharedPreferences(context).getString(KEY_PASSWORD, "")
            ?: ""
    }

    fun getCookie(context: Context): String {
        return PreferenceManager.getDefaultSharedPreferences(context).getString(KEY_COOKIE, "")
            ?: ""
    }

    fun getBoolean(context: Context?, key: String): Boolean {
        context?.let {
            return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(key, false)
        }
        return false
    }

    fun getBooleanDefaultTrue(context: Context?, key: String): Boolean {
        context?.let {
            return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(key, true)
        }
        return true
    }

    fun getBoolean(context: Context?, key: String, default: Boolean): Boolean {
        context?.let {
            return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(key, default)
        }
        return default
    }

    fun getString(context: Context, key: String): String? {
        return PreferenceManager.getDefaultSharedPreferences(context).getString(key, "")
    }

    fun getInt(context: Context, key: String): Int {
        return PreferenceManager.getDefaultSharedPreferences(context).getInt(key, -1)
    }

    fun removeLoginDataIfExist(context: Context) {
        val sPref = PreferenceManager.getDefaultSharedPreferences(context)
        val rememberMe = sPref.getBoolean(KEY_REMEMBER_ME, false)
        val username = sPref.getString(KEY_EMAIL_CACHE, "")
        val password = sPref.getString(KEY_PHONE_CACHE, "")
        sPref.edit().clear().apply()
        sPref.edit().putBoolean(KEY_REMEMBER_ME, rememberMe).putString(KEY_EMAIL_CACHE, username)
            .putString(KEY_PHONE_CACHE, password).apply()
    }

    /*fun getUser(context: Context): Users {
        val userStr =
            PreferenceManager.getDefaultSharedPreferences(context).getString(KEY_SESSION, "{}")
        return Gson().fromJson<Users>(userStr, Users::class.java)

    }*/

    fun putBoolean(context: Context?, key: String, enabled: Boolean) {
        context?.let {
            PreferenceManager.getDefaultSharedPreferences(context).edit()
                .putBoolean(key, enabled).apply()
        }
    }

    fun putString(context: Context?, key: String, enabled: String?) {
        context?.let {
            PreferenceManager.getDefaultSharedPreferences(context).edit()
                .putString(key, enabled).apply()
        }
    }
}
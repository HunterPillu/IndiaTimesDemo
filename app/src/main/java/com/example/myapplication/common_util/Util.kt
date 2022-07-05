package com.example.myapplication.common_util

import android.os.Build
import android.text.Html
import android.text.Spanned
import java.text.SimpleDateFormat
import java.util.*

object Util {
    const val KEY_ARTICLE = "article"
    const val KEY_NEWS_TYPE = "type"
    const val KEY_TAB_NUMBER = "key_tab_num"
    const val CODE_SUCCESS = 200
    const val CODE_LOAD_MORE = 300
    const val CODE_ERROR = 400

    fun getFormattedDateString(dateStr: String?): String? {
        try {
            val date: Date? = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:sszzz").parse(dateStr)
            val result = SimpleDateFormat("hh:mm a | dd-MM-yyyy ").format(date)
            return result
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return dateStr
    }

    fun getHtmlText(htmlString: String?): Spanned? {
        try {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Html.fromHtml(htmlString, Html.FROM_HTML_MODE_COMPACT)
            } else {
                Html.fromHtml(htmlString)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }


}
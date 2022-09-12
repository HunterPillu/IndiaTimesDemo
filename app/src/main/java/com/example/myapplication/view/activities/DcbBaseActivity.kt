package com.example.myapplication.view.activities

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.custom_toolbar.*

open class DcbBaseActivity : AppCompatActivity() {

    fun initToolbar(title: String, desc: String?) {
        try {
            tvToolbarTitle.text = title
            if (desc == null) {
                tvToolbarDesc.visibility = View.GONE
            } else {
                tvToolbarDesc.text = desc
                tvToolbarDesc.visibility = View.VISIBLE
            }

            ivToolbarBack.setOnClickListener {
                onBackPressed()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
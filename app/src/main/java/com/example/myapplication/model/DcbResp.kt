package com.example.myapplication.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class DcbResp(

    @SerializedName("success")
    @Expose
    var success: Boolean? = false,

    @SerializedName("message")
    @Expose
    var message: String? = null,

    )



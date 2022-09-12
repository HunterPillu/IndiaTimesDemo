package com.example.myapplication.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class DCBAuth (

    @SerializedName("email")
    @Expose
    var email: String? = null,
    @SerializedName("phone_number")
    @Expose
    var phoneNumber: String? = null,

    @SerializedName("association_id")
    @Expose
    var associationId: String? = null,

    @SerializedName("request_id")
    @Expose
    var requestId: String? = null,

    @SerializedName("google_payment_token")
    @Expose
    var gpToken: String? = null,

    @SerializedName("otp")
    @Expose
    var otp: String? = null,
)



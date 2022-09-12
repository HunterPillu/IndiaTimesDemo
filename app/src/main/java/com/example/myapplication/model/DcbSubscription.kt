package com.example.myapplication.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class DcbSubscription(

    @SerializedName("subscription_id")
    @Expose
    var subscriptionId: String? = null,

    @SerializedName("subscription_name")
    @Expose
    var subscriptionName: String? = null,

    @SerializedName("subscription_amount")
    @Expose
    var subscriptionAmount: String? = null,

    )



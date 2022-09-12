package com.example.myapplication.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class DcbPurchase(

    @SerializedName("phone_number")
    @Expose
    var phoneNumber: String? = null,

    @SerializedName("digital_service_name")
    @Expose
    var digitalServiceName: String? = null,

    @SerializedName("digital_service_id")
    @Expose
    var digitalServiceId: String? = null,

    @SerializedName("digital_service_cost")
    @Expose
    var digitalServiceCost: Int? = null,

    )



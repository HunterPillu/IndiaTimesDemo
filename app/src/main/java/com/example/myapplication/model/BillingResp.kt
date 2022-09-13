package com.example.myapplication.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class BillingResp(

    @SerializedName("is_success")
    @Expose
    var success: Boolean? = false,

    @SerializedName("message")
    @Expose
    var message: String? = null,

    @SerializedName("phone_number")
    @Expose
    var phone_number: Boolean? = false,

    @SerializedName("current_billing_amount")
    @Expose
    var current_billing_amount: Long? = null,

    @SerializedName("current_statement")
    @Expose
    var transactions: MutableList<DcbTransaction>? = null,

    @SerializedName("created_at")
    @Expose
    var created_at: String? = null,

    @SerializedName("updated_at")
    @Expose
    var updated_at: String? = null,

    )



package com.example.myapplication.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class DcbTransaction(


    @SerializedName("transaction_date")
    @Expose
    var transactionDate: String? = null,

    @SerializedName("transaction_amount")
    @Expose
    var transactionAmount: Long? = null,

    @SerializedName("type_of_transaction")
    @Expose
    var typeOfTransaction: String? = null,

    @SerializedName("transaction_item")
    @Expose
    var transactionItem: String? = null,

    )



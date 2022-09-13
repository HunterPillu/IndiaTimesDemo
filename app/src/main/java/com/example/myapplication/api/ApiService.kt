package com.example.myapplication.api

import com.example.myapplication.model.*
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    @GET("rssfeedstopstories.cms")
    suspend fun getTopStories(): Response<Feed>

    @GET("rssfeeds/-2128936835.cms")
    suspend fun getIndiaNews(): Response<Feed>

    @GET("rssfeeds/296589292.cms")
    suspend fun getWorldNews(): Response<Feed>

    @GET("rssfeeds/4719148.cms")
    suspend fun getSportsNews(): Response<Feed>

    @POST("user/authenticate")
    suspend fun authenticate(@Body body: DCBAuth): Response<DCBAuth>

    @POST("user/association")
    suspend fun association(@Body body: DCBAuth): Response<OtpResp>

    @POST("user/purchase")
    suspend fun purchase(@Body body: DcbPurchase): Response<OtpResp>

    @GET("user/subscription_details")
    suspend fun subscriptionDetails(): Response<MutableList<DcbSubscription>>

    @GET("user/wallet")
    suspend fun walletDetails(@Query("phone_number") phone_number: String): Response<BillingResp>
}

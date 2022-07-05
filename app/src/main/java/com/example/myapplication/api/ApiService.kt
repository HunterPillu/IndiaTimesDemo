package com.example.myapplication.api

import com.example.myapplication.model.Feed
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("rssfeedstopstories.cms")
    suspend fun getTopStories(): Response<Feed>

    @GET("rssfeeds/-2128936835.cms")
    suspend fun getIndiaNews(): Response<Feed>

    @GET("rssfeeds/296589292.cms")
    suspend fun getWorldNews(): Response<Feed>

    @GET("rssfeeds/4719148.cms")
    suspend fun getSportsNews(): Response<Feed>
}

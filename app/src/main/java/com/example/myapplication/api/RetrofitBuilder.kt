package com.example.myapplication.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitBuilder {
    //private const val BASE_URL: String = "https://timesofindia.indiatimes.com/"
    private const val BASE_URL: String = "http://40.81.224.78:8080/"

    fun getApiService(): ApiService {
        return getRetrofit().create(ApiService::class.java)

    }

    private fun getRetrofit(): Retrofit {

        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client: OkHttpClient = OkHttpClient.Builder().addInterceptor(interceptor)
            .connectTimeout(5, TimeUnit.SECONDS)
            .readTimeout(5, TimeUnit.SECONDS)
            .writeTimeout(5, TimeUnit.SECONDS)
            .build()

        return Retrofit.Builder().baseUrl(BASE_URL)
            //.addConverterFactory(SimpleXmlConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(client).build()
    }


}
package com.teststa.remote

import com.teststa.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://api.github.com/"
    private val loggingInterceptor = with(HttpLoggingInterceptor()) {
        if (BuildConfig.DEBUG)
            setLevel(HttpLoggingInterceptor.Level.BODY)
        else
            setLevel(HttpLoggingInterceptor.Level.NONE)
    }

    private val client = with(OkHttpClient.Builder()) {
        addInterceptor(loggingInterceptor)
        build()
    }
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    val apiService: ApiService = retrofit.create(ApiService::class.java)
}
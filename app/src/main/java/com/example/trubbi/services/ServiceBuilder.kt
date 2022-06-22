package com.example.trubbi.services

import android.preference.PreferenceManager
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class ServiceBuilder(token: String?) {
    private val URL = "http://192.168.0.13:3060"

    var client = OkHttpClient.Builder().addInterceptor { chain ->
        val newRequest: Request = chain.request().newBuilder()
            .addHeader(
                "Authorization",
                "Bearer $token"
            )
            .build()
        chain.proceed(newRequest)
    }.build()


    private val builder: Retrofit.Builder = Retrofit.Builder()
        .baseUrl(URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)

    private val retrofit: Retrofit = builder.build()

    fun <T> buildService(serviceType: Class<T>): T {
        return retrofit.create(serviceType)
    }
}
package com.example.trubbi.services

import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object ServiceBuilder {
    private  const val URL = "http://192.168.0.13:3060"
    //private val okhttp: OkHttpClient.Builder = OkHttpClient.Builder()


    var client = OkHttpClient.Builder().addInterceptor { chain ->
        val newRequest: Request = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6ImV6ZWJpazIwMTVAZ21haWwuY29tIiwiaWQiOjEsInJvbGUiOiJ0b3VyaXN0IiwiaWF0IjoxNjU1NzUyOTMxLCJleHAiOjE2NTU3NzA5MzF9.d4D-lZvIGGeLYB7qT0b5ZgpzWIIZ15Id3zn7r9duwlo")
            .build()
        chain.proceed(newRequest)
    }.build()



    private val builder: Retrofit.Builder = Retrofit.Builder()
                                            .baseUrl(URL)
                                            .addConverterFactory(GsonConverterFactory.create())
                                            .client(client)

    private val retrofit: Retrofit = builder.build()

    fun <T> buildService(serviceType: Class<T>): T{
        return retrofit.create(serviceType)
    }
}
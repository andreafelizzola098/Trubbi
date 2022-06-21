package com.example.trubbi.services

import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object ServiceBuilder {
    private  const val URL = "http://192.168.0.76:3060"
    //private val okhttp: OkHttpClient.Builder = OkHttpClient.Builder()


    var client = OkHttpClient.Builder().addInterceptor { chain ->
        val newRequest: Request = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6ImFuZGlAZ21haWwuY29tIiwiaWQiOjEsInJvbGUiOiJ0b3VyaXN0IiwiaWF0IjoxNjU1ODA5MzA3LCJleHAiOjE2NTU4MjczMDd9.mgNee_UcE08yY8ns5vkSTFy8EFEI2VA6yQtI501mEwI")
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
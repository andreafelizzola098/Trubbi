package com.example.trubbi.interfaces

import com.example.trubbi.data.EventResponse
import com.example.trubbi.data.LoginTouristResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface APILoginService {

    @POST("/tourists/login")
    fun login(@Body loginTouristResponse: LoginTouristResponse): Call<LoginTouristResponse>

    @POST("/tourists")
    fun createTourist(@Body loginTouristResponse: LoginTouristResponse): Call<LoginTouristResponse>
}
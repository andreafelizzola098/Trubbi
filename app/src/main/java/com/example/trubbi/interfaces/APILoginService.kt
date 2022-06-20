package com.example.trubbi.interfaces

import com.example.trubbi.data.LoginTouristResponse
import com.example.trubbi.data.RegisterTouristResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface APILoginService {

    @POST("/tourists/login")
    fun login(@Body loginTouristResponse: LoginTouristResponse): Call<LoginTouristResponse>

    @POST("/tourists")
    fun createTourist(@Body registerTouristResponse: RegisterTouristResponse): Call<RegisterTouristResponse>
}
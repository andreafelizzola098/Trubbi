package com.example.trubbi.interfaces

import com.example.trubbi.data.EventResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface APIEventService {
    @GET("/events/{title}")
    suspend fun getSearchEvent(@Path("title")query:String): Call<EventResponse>
}
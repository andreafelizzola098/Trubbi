package com.example.trubbi.interfaces

import com.example.trubbi.data.EventResponse
import com.example.trubbi.data.EventsListResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Url

interface APIEventService {

    @GET("/events/{id}")
    fun getEventById(@Path("id")id:Number): Call<EventResponse>

    @GET("/events/{title}")
    suspend fun getSearchEvent(@Path("title")name:String):Call<EventResponse>

    @GET("/events")
    fun getEvents():Call<EventResponse>

    @GET("/categories/{id}/events")
    fun getEventsByCategory(@Path("category")name:String):Call<EventResponse>

    @GET("/tourist/{id}/favorites")
    fun getFavoritesEvents(@Path("name")name:String):Call<EventResponse>

    @GET("/events/{id}/schedule")
    fun getScheduleEvents(@Path("name")name:String):Call<EventResponse>

    @GET("/events/{id}/history")
    fun getHistoryEvents(@Path("name")name:String):Call<EventResponse>

    @GET("/events/{id}/nearby")
    fun getNearbyEvents(@Path("name")name:String):Call<EventResponse>
}
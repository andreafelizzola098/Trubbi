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
    fun getEvents():Call<List<EventResponse>>

    @GET("/categories/{id}/events")
    fun getEventsByCategory(@Path("id")id:Number):Call<List<EventResponse>>

    @GET("/favorites")
    fun getFavoritesEvents(@Path("id")id:Number):Call<List<EventResponse>>

    @GET("/tourists/{id}/events")
    fun getScheduleEvents(@Path("id")id:Number):Call<List<EventResponse>>

    @GET("/tourists/{id}/history")
    fun getHistoryEvents(@Path("id")id:Number):Call<List<EventResponse>>

    @GET("/tourists/{id}/nearby")
    fun getNearbyEvents(@Path("id")id:Number):Call<List<EventResponse>>
}
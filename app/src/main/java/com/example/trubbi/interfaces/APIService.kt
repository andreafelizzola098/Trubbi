package com.example.trubbi.interfaces

import com.example.trubbi.data.EventResponse
import com.example.trubbi.data.EventsListResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Url

interface APIService {
    @GET("/events/{id}")
    fun getEventById(@Path("id")id:Number): Call<EventResponse>
    @GET
    suspend fun getSearchEvent(@Url url:String):Response<EventsListResponse>
    @GET
    fun getEvents(@Url url:String):Response<EventsListResponse>
    @GET
    fun getEventsByCategory()
    @GET
    fun getFavoritesEvents()
    @GET
    fun getScheduleEvents()
    @GET
    fun getHistoryEvents()
    @GET
    fun getNearbyEvents()
}
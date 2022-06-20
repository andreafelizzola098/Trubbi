package com.example.trubbi.interfaces

import com.example.trubbi.data.CategoryResponse
import com.example.trubbi.data.EventResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface APIEventService {

    @GET("/events/{id}")
    fun getEventById(@Path("id")id:Number): Call<EventResponse>

    @GET("/events/{title}")
    suspend fun getSearchEvent(@Path("title")name:String):Call<EventResponse>

    @GET("/events")
    fun getEvents():Call<List<EventResponse>>

    @GET("/categories/")
    fun getCategories():Call<List<CategoryResponse>>

    @GET("/categories/{id}/events")
    fun getEventsByCategory(@Path("id")id:Number):Call<List<EventResponse>>

    @GET("/favorites")
    fun getFavoritesEvents():Call<List<EventResponse>>

    @GET("/schedule")
    fun getScheduleEvents():Call<List<EventResponse>>

    @GET("/schedule/history")
    fun getHistoryEvents():Call<List<EventResponse>>
}
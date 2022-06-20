package com.example.trubbi.interfaces

import com.example.trubbi.data.CategoryList
import com.example.trubbi.data.CategoryResponse
import com.example.trubbi.data.EventResponse
import com.example.trubbi.data.Schedule
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
    fun getCategories():Call<CategoryList>

    @GET("/categories/{id}/events")
    fun getEventsByCategory(@Path("id")id:Number):Call<List<EventResponse>>

    @GET("/favorites")
    fun getFavoritesEvents():Call<List<Schedule>>

    @GET("/schedule")
    fun getScheduleEvents():Call<List<Schedule>>

    @GET("/schedule/history")
    fun getHistoryEvents():Call<List<EventResponse>>

    @GET("/tourists/categories")
    fun getTouristCategories():Call<List<CategoryResponse>>
}
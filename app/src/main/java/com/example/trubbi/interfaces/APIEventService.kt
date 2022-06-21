package com.example.trubbi.interfaces

import com.example.trubbi.data.*
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface APIEventService {

    @GET("/events/{id}")
    fun getEventById(@Path("id")id:Number): Call<EventResponse>

    @GET("/events/{title}")
    fun getSearchEvent(@Path("title")name:String):Call<List<EventResponse>>

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
    fun getHistoryEvents():Call<List<Schedule>>

    @GET("/tourists/categories")
    fun getTouristCategories():Call<List<CategoryResponse>>

    @POST("/tourists/categories")
    fun scheduleEvent(@Path("id")id:Number):Call<ScheduleDetails>
}
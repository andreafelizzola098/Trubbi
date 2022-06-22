package com.example.trubbi.interfaces

import com.example.trubbi.data.*
import retrofit2.Call
import retrofit2.http.*

interface APIEventService {

    @GET("/events/{id}")
    fun getEventById(@Path("id")id:Number): Call<EventResponse>

    @GET("/events/byName")
    fun getEventByTitle(@Query("name")name:String?):Call<List<EventResponse>>

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

    @POST("/schedule/{id}")
    fun scheduleEvent(@Path("id")id:Number):Call<ScheduleDetails>

    @DELETE("/schedule/{id}")
    fun deleteScheduleEvent(@Path("id")id:Number):Call<ScheduleDetails>

    @POST("/favorites/{id}")
    fun favoriteEvent(@Path("id")id:Number):Call<ScheduleDetails>

    @DELETE("/favorites/{id}")
    fun deleteFavoriteEvent(@Path("id")id:Number):Call<ScheduleDetails>

    @PUT("/tourists")
    fun updateTourist(@Body updateTouristRequest: UpdateTouristRequest):Call<UpdateTourist>

    @GET("/tourists/me")
    fun getTourist():Call<UpdateTourist>
}
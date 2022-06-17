package com.example.trubbi.data

import com.example.trubbi.entities.Event
import com.google.gson.annotations.SerializedName

data class EventsListResponse(
    @SerializedName("eventos") var events:List<Event>
    )

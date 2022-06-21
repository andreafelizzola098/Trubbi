package com.example.trubbi.data

import com.google.gson.annotations.SerializedName

data class Schedule( @SerializedName("tourist") var id: Number, @SerializedName("event") var event: EventResponse) {
}
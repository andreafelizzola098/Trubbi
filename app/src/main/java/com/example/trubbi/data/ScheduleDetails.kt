package com.example.trubbi.data

import com.google.gson.annotations.SerializedName

data class ScheduleDetails(@SerializedName("tourist") var id: Number,
                           @SerializedName("event") var event: Number,
                           @SerializedName("scheduled") var scheduled: Boolean,
                           @SerializedName("favourite") var favourite: Boolean) {
}
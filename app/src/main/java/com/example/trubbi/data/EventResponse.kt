package com.example.trubbi.data

import com.google.gson.annotations.SerializedName

data class EventResponse(
    @SerializedName("id") var id: Number,
    @SerializedName("title") var title: String,
    @SerializedName("description") var description: String,
    @SerializedName("photo") var photo: String,
    @SerializedName("start_date") var start_date: Long,
    @SerializedName("end_date") var end_date: Long,
    @SerializedName("public") var public: Boolean,
    @SerializedName("address") var address: String,
    @SerializedName("category") var category : List<CategoryResponse>
)

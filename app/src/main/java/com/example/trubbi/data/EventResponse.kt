package com.example.trubbi.data

import com.google.gson.annotations.SerializedName
import java.util.*

data class EventResponse(
    @SerializedName("id") var id: Number,
    @SerializedName("title") var title: String,
    @SerializedName("description") var description: String,
    @SerializedName("photo") var photo: String,
    //@SerializedName("start_date") var start_date: Date,
    //@SerializedName("end_date") var end_date: Date,
    //@SerializedName("provider") var provider: ProviderResponse,
    //@SerializedName("tourist") var tourist : List<TouristResponse>,
    //@SerializedName("category") var category : List<CategoryResponse>
)

package com.example.trubbi.data

import com.google.gson.annotations.SerializedName

data class CategoryResponse(
    @SerializedName("id") var id: Number,
    @SerializedName("name") var name : String
)

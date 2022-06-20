package com.example.trubbi.data

import com.google.gson.annotations.SerializedName

data class RegisterTouristResponse(
    @SerializedName("email") var email: String,
    @SerializedName("password") var password: String,
    @SerializedName("name") var name: String
)

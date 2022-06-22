package com.example.trubbi.data

import com.google.gson.annotations.SerializedName

data class LoginTouristResponse(
    @SerializedName("email") var email: String,
    @SerializedName("password") var password: String,
    @SerializedName("access_token") var accessToken: String
)

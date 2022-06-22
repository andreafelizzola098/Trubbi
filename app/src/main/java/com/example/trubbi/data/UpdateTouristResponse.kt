package com.example.trubbi.data

import com.google.gson.annotations.SerializedName

data class UpdateTouristResponse(@SerializedName("email") var email: String,
                                 @SerializedName("name") var name: String,
                                 @SerializedName("id") var id: Long) {
}
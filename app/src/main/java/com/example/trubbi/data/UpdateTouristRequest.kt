package com.example.trubbi.data

import com.google.gson.annotations.SerializedName

data class UpdateTouristRequest(@SerializedName("name") var name: String) {
}
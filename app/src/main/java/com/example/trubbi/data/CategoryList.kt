package com.example.trubbi.data

import com.google.gson.annotations.SerializedName

data class CategoryList(@SerializedName("result") var categoryList: List<CategoryResponse>) {
}
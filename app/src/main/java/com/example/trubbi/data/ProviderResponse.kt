package com.example.trubbi.data

import java.util.*

data class ProviderResponse(
    var id: Number,
    var name: String,
    var status: String,
    var createdAt: Date,
    var updatedAt: Date,
    var deleted_at: Date
)

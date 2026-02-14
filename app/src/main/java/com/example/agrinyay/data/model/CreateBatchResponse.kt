package com.example.agrinyay.data.model

import com.google.gson.annotations.SerializedName

data class CreateBatchResponse(

    @SerializedName("_id")
    val batchId: String,

    val vehicleId: String,
    val cropType: String,
    val cropQuantityKg: Int,
    val originLocation: String,
    val status: String,
    val createdAt: String
)

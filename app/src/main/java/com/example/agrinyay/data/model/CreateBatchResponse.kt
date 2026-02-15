package com.example.agrinyay.data.model

import com.google.gson.annotations.SerializedName

data class CreateBatchResponse(
    @SerializedName("success")
    val success: Boolean,

    @SerializedName("message")
    val message: String,

    @SerializedName("data")
    val data: BatchData // This was the missing reference!
)
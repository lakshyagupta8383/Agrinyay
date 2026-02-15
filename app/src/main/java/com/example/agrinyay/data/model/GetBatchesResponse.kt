package com.example.agrinyay.data.model

import com.google.gson.annotations.SerializedName

data class GetBatchesResponse(
    @SerializedName("success")
    val success: Boolean,

    @SerializedName("data")
    val data: List<BatchData> // This was the missing reference!
)
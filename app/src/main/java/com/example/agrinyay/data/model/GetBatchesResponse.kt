package com.example.agrinyay.data.model

import com.google.gson.annotations.SerializedName

data class GetBatchesResponse(
    val success: Boolean,

    // This tells Retrofit: "The list is inside the 'data' key"
    @SerializedName("data")
    val batches: List<BatchData>
)
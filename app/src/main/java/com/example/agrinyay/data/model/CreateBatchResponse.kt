package com.example.agrinyay.data.model

import com.google.gson.annotations.SerializedName

data class CreateBatchResponse(
    val success: Boolean, // Matches res.json({ success: true ... })
    val data: BatchData   // Matches res.json({ ... data: batch })
)
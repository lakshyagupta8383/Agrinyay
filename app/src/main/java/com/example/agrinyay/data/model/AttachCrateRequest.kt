package com.example.agrinyay.data.model

import com.google.gson.annotations.SerializedName

data class AttachCrateRequest(
    @SerializedName("qr_code")
    val qrCode: String,
    @SerializedName("condition")
    val condition: String,
    @SerializedName("batch_id")
    val batchId: String
)
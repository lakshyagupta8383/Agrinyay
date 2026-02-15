package com.example.agrinyay.data.model

import com.google.gson.annotations.SerializedName

data class BatchData(
    @SerializedName("batch_uid")
    val batchId: String,

    @SerializedName("farmer_uid")
    val farmerId: String,

    @SerializedName("crop_type")
    val cropType: String,

    @SerializedName("crop_quantity")
    val cropQuantity: Int,

    @SerializedName("hardware")
    val hardware: String,

    @SerializedName("createdAt")
    val createdAt: String,

    @SerializedName("status")
    val status: String = "Created"
)
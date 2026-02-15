package com.example.agrinyay.data.model

import com.google.gson.annotations.SerializedName

data class CreateBatchRequest(
    @SerializedName("crop_type")
    val cropType: String, // Matches Lakshya's req.body.crop_type

    @SerializedName("crop_quantity")
    val cropQuantity: Int, // Matches Lakshya's req.body.crop_quantity

    @SerializedName("hardware")
    val hardware: String // Matches Lakshya's req.body.hardware
)
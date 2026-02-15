package com.example.agrinyay.data.model

import com.google.gson.annotations.SerializedName

data class Crate(
    @SerializedName("qr_code")
    val qrCode: String,

    @SerializedName("condition")
    val condition: String,

    @SerializedName("_id")
    val id: String? = null
)
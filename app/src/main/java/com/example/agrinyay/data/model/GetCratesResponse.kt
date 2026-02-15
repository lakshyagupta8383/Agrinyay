package com.example.agrinyay.data.model

import com.google.gson.annotations.SerializedName

data class GetCratesResponse(
    @SerializedName("batch_uid") val batchId: String,
    @SerializedName("total") val total: Int,
    @SerializedName("crates") val crates: List<Crate>
)
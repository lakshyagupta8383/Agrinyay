package com.example.agrinyay.data.model

data class CreateBatchRequest(
    val farmerId: String,
    val cropType: String,
    val quantity: String
)

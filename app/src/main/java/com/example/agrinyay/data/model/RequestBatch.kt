package com.example.agrinyay.data.model
data class RequestBatch(
    val farmerId:String,
    val batchId:String,
    val fruitType:String,
    val weight:Double,
    val location:String,
    val createdAt:Long
)

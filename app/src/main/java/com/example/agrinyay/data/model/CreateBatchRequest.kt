package com.example.agrinyay.data.model

data class CreateBatchRequest(
    val vehicleId:String,
    val cropType:String,
    val cropQuantityKg:Int,
    val originLocation:String
)

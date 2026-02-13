package com.example.agrinyay.repository

import kotlinx.coroutines.delay

class BatchRepository{

    suspend fun createBatch(
        farmerId:String,
        crateId:String,
        crop:String,
        quantity:String
    ):Result<String>{

        // Simulate backend call
        delay(1000)

        // Later replace with Retrofit call
        return Result.success("BATCH_${System.currentTimeMillis()}")
    }
}

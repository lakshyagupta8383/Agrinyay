package com.example.agrinyay.data.remote

import com.example.agrinyay.data.model.CreateBatchRequest
import com.example.agrinyay.data.model.CreateBatchResponse

interface BackendService {

    suspend fun createBatch(
        request: CreateBatchRequest
    ): CreateBatchResponse
}

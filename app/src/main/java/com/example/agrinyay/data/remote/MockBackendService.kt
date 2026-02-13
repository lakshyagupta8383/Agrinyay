package com.example.agrinyay.data.remote

import com.example.agrinyay.data.model.CreateBatchRequest
import com.example.agrinyay.data.model.CreateBatchResponse
import kotlinx.coroutines.delay
import java.util.UUID

class MockBackendService : BackendService {

    override suspend fun createBatch(
        request: CreateBatchRequest
    ): CreateBatchResponse {

        delay(1500) // simulate network delay

        return CreateBatchResponse(
            batchId = UUID.randomUUID().toString(),
            message = "Batch created successfully"
        )
    }
}

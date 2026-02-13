package com.example.agrinyay.data.remote

import com.example.agrinyay.data.model.*

interface BackendService {

    suspend fun createBatch(
        request:CreateBatchRequest
    ):GenericResponse<CreateBatchResponse>

    suspend fun attachCrate(
        request:AttachCrateRequest
    ):GenericResponse<Unit>
}

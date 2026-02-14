package com.example.agrinyay.repository

import com.example.agrinyay.data.model.CreateBatchRequest
import com.example.agrinyay.data.model.CreateBatchResponse
import com.example.agrinyay.data.model.AttachCrateRequest
import com.example.agrinyay.data.remote.RetrofitClient
import com.example.agrinyay.utils.ApiResult

class AgriRepository {

    suspend fun createBatch(
        token: String,
        request: CreateBatchRequest
    ): ApiResult<CreateBatchResponse> {

        return try {

            val response = RetrofitClient.api.createBatch(
                token = token,
                request = request
            )

            if (response.isSuccessful && response.body() != null) {
                ApiResult.Success(response.body()!!)
            } else {
                ApiResult.Error("Error creating batch")
            }

        } catch (e: Exception) {
            ApiResult.Error(e.message ?: "Unknown error")
        }
    }

    suspend fun attachCrate(
        request: AttachCrateRequest
    ): ApiResult<Unit> {

        return try {

            val response = RetrofitClient.api.attachCrate(request)

            if (response.isSuccessful) {
                ApiResult.Success(Unit)
            } else {
                ApiResult.Error("Error attaching crate")
            }

        } catch (e: Exception) {
            ApiResult.Error(e.message ?: "Unknown error")
        }
    }
}

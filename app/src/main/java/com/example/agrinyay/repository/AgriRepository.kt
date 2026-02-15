package com.example.agrinyay.repository

import com.example.agrinyay.data.model.*
import com.example.agrinyay.data.remote.RetrofitClient
import com.example.agrinyay.utils.ApiResult

class AgriRepository {

    suspend fun createBatch(token: String, request: CreateBatchRequest): ApiResult<BatchData> {
        return try {
            val response = RetrofitClient.api.createBatch(token, request)
            if (response.isSuccessful && response.body() != null) {
                val wrapper = response.body()!!
                if (wrapper.success) ApiResult.Success(wrapper.data)
                else ApiResult.Error("Backend returned success: false")
            } else {
                ApiResult.Error("Error: ${response.code()} ${response.message()}")
            }
        } catch (e: Exception) {
            ApiResult.Error(e.message ?: "Unknown Error")
        }
    }

    // FIX: This function correctly accepts the token now
    suspend fun getBatches(token: String, farmerId: String): ApiResult<List<BatchData>> {
        return try {
            // FIX: Passes the token to the API interface
            val response = RetrofitClient.api.getBatches(token, farmerId)

            if (response.isSuccessful && response.body() != null) {
                val wrapper = response.body()!!
                // Maps the "data" list from JSON to your "batches" list
                if (wrapper.success) ApiResult.Success(wrapper.batches)
                else ApiResult.Error("Backend returned success: false")
            } else {
                ApiResult.Error("Error: ${response.code()} ${response.message()}")
            }
        } catch (e: Exception) {
            ApiResult.Error(e.message ?: "Unknown Error")
        }
    }

    suspend fun attachCrate(token: String, batchId: String, request: AttachCrateRequest): ApiResult<Unit> {
        return try {
            val response = RetrofitClient.api.attachCrate(token, batchId, request)
            if (response.isSuccessful) ApiResult.Success(Unit)
            else ApiResult.Error("Error: ${response.code()}")
        } catch (e: Exception) {
            ApiResult.Error(e.localizedMessage ?: "Unknown Error")
        }
    }
}
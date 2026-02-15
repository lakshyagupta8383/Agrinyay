package com.example.agrinyay.repository

import com.example.agrinyay.data.model.*
import com.example.agrinyay.data.remote.RetrofitClient
import com.example.agrinyay.utils.ApiResult

class AgriRepository {

    suspend fun createBatch(token: String, request: CreateBatchRequest): ApiResult<BatchData> {
        return try {
            val response = RetrofitClient.api.createBatch(token, request)
            if (response.isSuccessful && response.body() != null) {
                ApiResult.Success(response.body()!!.data)
            } else {
                ApiResult.Error("Failed: ${response.code()}")
            }
        } catch (e: Exception) {
            ApiResult.Error(e.message ?: "Unknown error")
        }
    }

    suspend fun getBatches(token: String, farmerId: String): ApiResult<List<BatchData>> {
        return try {
            val response = RetrofitClient.api.getBatches(token, farmerId)
            if (response.isSuccessful && response.body() != null) {
                ApiResult.Success(response.body()!!.data)
            } else {
                ApiResult.Error("Failed: ${response.code()}")
            }
        } catch (e: Exception) {
            ApiResult.Error(e.message ?: "Unknown error")
        }
    }

    suspend fun attachCrate(token: String, batchId: String, request: AttachCrateRequest): ApiResult<Unit> {
        return try {
            val response = RetrofitClient.api.attachCrate(token, batchId, request)
            if (response.isSuccessful) {
                ApiResult.Success(Unit)
            } else {
                ApiResult.Error("Failed: ${response.code()}")
            }
        } catch (e: Exception) {
            ApiResult.Error(e.message ?: "Unknown error")
        }
    }

    // --- NEW FUNCTION ADDED HERE ---
    suspend fun getCrates(token: String, batchId: String): ApiResult<GetCratesResponse> {
        return try {
            val response = RetrofitClient.api.getCrates(token, batchId)
            if (response.isSuccessful && response.body() != null) {
                ApiResult.Success(response.body()!!)
            } else {
                ApiResult.Error("Failed to fetch crates: ${response.code()}")
            }
        } catch (e: Exception) {
            ApiResult.Error(e.message ?: "Unknown error")
        }
    }
}
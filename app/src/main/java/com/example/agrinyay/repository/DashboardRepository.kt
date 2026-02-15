package com.example.agrinyay.repository

import com.example.agrinyay.data.model.DashboardResponse
import com.example.agrinyay.data.remote.RetrofitClient
import com.example.agrinyay.utils.ApiResult

class DashboardRepository {

    suspend fun getDashboard(farmerId: String): ApiResult<DashboardResponse> {
        return try {
            val response = RetrofitClient.api.getDashboard(farmerId)
            if (response.isSuccessful && response.body() != null) {
                ApiResult.Success(response.body()!!)
            } else {
                ApiResult.Error("Error: ${response.code()}")
            }
        } catch (e: Exception) {
            ApiResult.Error(e.message ?: "Unknown error")
        }
    }
}
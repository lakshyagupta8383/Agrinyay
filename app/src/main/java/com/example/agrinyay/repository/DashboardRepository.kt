package com.example.agrinyay.repository

import com.example.agrinyay.data.remote.RetrofitClient
import com.example.agrinyay.data.remote.safeApiCall

class DashboardRepository {

    private val api = RetrofitClient.api

    suspend fun getDashboard(farmerId:String) =
        safeApiCall {
            api.getDashboard(farmerId)
        }
}

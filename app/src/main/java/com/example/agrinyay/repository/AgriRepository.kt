package com.example.agrinyay.data.repository

import com.example.agrinyay.data.model.*
import com.example.agrinyay.data.remote.BackendApi
import com.example.agrinyay.data.remote.RetrofitClient
import com.example.agrinyay.data.remote.safeApiCall

class AgriRepository(
    private val api:BackendApi=RetrofitClient.api
){

    suspend fun createBatch(request:CreateBatchRequest)=
        safeApiCall { api.createBatch(request) }

    suspend fun attachCrate(request:AttachCrateRequest)=
        safeApiCall { api.attachCrate(request) }

    suspend fun createVehicle(request:CreateVehicleRequest)=
        safeApiCall { api.createVehicle(request) }
}

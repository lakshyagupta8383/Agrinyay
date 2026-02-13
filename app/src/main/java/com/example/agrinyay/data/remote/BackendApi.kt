package com.example.agrinyay.data.remote

import com.example.agrinyay.data.model.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface BackendApi {

    @POST("api/v1/vehicles")
    suspend fun createVehicle(
        @Body request:CreateVehicleRequest
    ):Response<GenericResponse<Unit>>

    @POST("api/v1/batches")
    suspend fun createBatch(
        @Body request:CreateBatchRequest
    ):Response<GenericResponse<CreateBatchResponse>>

    @POST("api/v1/crates/attach")
    suspend fun attachCrate(
        @Body request:AttachCrateRequest
    ):Response<GenericResponse<Unit>>
}

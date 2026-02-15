package com.example.agrinyay.data.remote

import com.example.agrinyay.data.model.*
import retrofit2.Response
import retrofit2.http.*

interface BackendApi {

    @POST("api/batches")
    suspend fun createBatch(
        @Header("Authorization") token: String,
        @Body request: CreateBatchRequest
    ): Response<CreateBatchResponse>

    @GET("api/batches")
    suspend fun getBatches(
        @Header("Authorization") token: String,
        @Query("farmerId") farmerId: String
    ): Response<GetBatchesResponse>

    @GET("api/dashboard/{farmerId}")
    suspend fun getDashboard(
        @Path("farmerId") farmerId: String
    ): Response<DashboardResponse>

    @POST("api/batches/{batchId}/crates")
    suspend fun attachCrate(
        @Header("Authorization") token: String,
        @Path("batchId") batchId: String,
        @Body request: AttachCrateRequest
    ): Response<Unit>

    // --- NEW METHOD ADDED HERE ---
    @GET("api/batches/{batchId}/crates")
    suspend fun getCrates(
        @Header("Authorization") token: String,
        @Path("batchId") batchId: String
    ): Response<GetCratesResponse>
}
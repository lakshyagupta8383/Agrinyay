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

    // ⚠️ CRITICAL: Use this if Lakshya adds 'router.get("/", ...)' to the batches route.
    // The farmerId should likely be a QUERY parameter, not a path parameter,
    // because the backend can get the UID from the token itself (req.user.uid).
    @GET("api/batches")
    suspend fun getBatches(
        @Header("Authorization") token: String,
        @Query("farmerId") farmerId: String // Sending it as a query is safer/standard
    ): Response<GetBatchesResponse>

    @GET("api/dashboard/{farmerId}")
    suspend fun getDashboard(
        @Path("farmerId") farmerId: String
    ): Response<DashboardResponse>

    @POST("api/crate/add/{batchId}")
    suspend fun attachCrate(
        @Header("Authorization") token: String,
        @Path("batchId") batchId: String,
        @Body request: AttachCrateRequest
    ): Response<Unit>
}
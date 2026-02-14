package com.example.agrinyay.data.remote

import com.example.agrinyay.data.model.CreateBatchRequest
import com.example.agrinyay.data.model.CreateBatchResponse
import com.example.agrinyay.data.model.AttachCrateRequest
import com.example.agrinyay.data.model.DashboardResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface BackendApi {

    @POST("api/batches")
    suspend fun createBatch(
        @Header("Authorization") token: String,
        @Header("Content-Type") contentType: String = "application/json",
        @Body request: CreateBatchRequest
    ): Response<CreateBatchResponse>

    @POST("api/crates")
    suspend fun attachCrate(
        @Body request: AttachCrateRequest
    ): Response<Unit>

    @GET("api/dashboard/{farmerId}")
    suspend fun getDashboard(
        @Path("farmerId") farmerId: String
    ): Response<DashboardResponse>
}

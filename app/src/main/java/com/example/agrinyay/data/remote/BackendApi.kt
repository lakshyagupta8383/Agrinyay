package com.example.agrinyay.data.remote

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.Response
import com.example.agrinyay.data.model.*

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

    @GET("farmer/dashboard/{farmerId}")
    suspend fun getDashboard(
        @Path("farmerId") farmerId:String
    ):Response<GenericResponse<DashboardResponse>>
}

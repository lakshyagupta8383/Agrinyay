package com.example.agrinyay.data.remote
import com.example.agrinyay.data.model.RequestBatch
import com.example.agrinyay.data.model.RequestCrate
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
interface BackendApi {

    @POST("create-batch")
    suspend fun createBatch(
        @Body request: RequestBatch
    ): Response<Unit>

    @POST("add-crate")
    suspend fun addCrate(
        @Body request: RequestCrate
    ): Response<Unit>
}

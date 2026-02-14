package com.example.agrinyay.data.remote

import com.example.agrinyay.data.model.GenericResponse
import com.example.agrinyay.utils.ApiResult
import retrofit2.Response
import retrofit2.HttpException
import java.io.IOException

suspend fun <T> safeApiCall(
    apiCall: suspend () -> Response<GenericResponse<T>>
): ApiResult<T> {

    return try {

        val response = apiCall()

        if (response.isSuccessful) {

            val body = response.body()

            if (body != null && body.success) {
                ApiResult.Success(body.data)
            } else {
                ApiResult.Error(body?.message ?: "Something went wrong")
            }

        } else {
            ApiResult.Error(response.message())
        }

    } catch (e: HttpException) {
        ApiResult.Error(e.message())
    } catch (e: IOException) {
        ApiResult.Error("Network Error")
    } catch (e: Exception) {
        ApiResult.Error("Unknown Error")
    }
}

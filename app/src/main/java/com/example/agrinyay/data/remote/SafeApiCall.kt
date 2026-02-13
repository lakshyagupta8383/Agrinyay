package com.example.agrinyay.data.remote

import com.example.agrinyay.data.model.GenericResponse
import com.example.agrinyay.util.ApiResult
import retrofit2.Response

suspend fun <T> safeApiCall(
    apiCall:suspend ()->Response<GenericResponse<T>>
):ApiResult<T>{

    return try{
        val response=apiCall()

        if(response.isSuccessful){
            val body=response.body()
            if(body!=null && body.success){
                ApiResult.Success(body.data)
            }else{
                ApiResult.Error("Server error")
            }
        }else{
            ApiResult.Error("HTTP ${response.code()}")
        }

    }catch(e:Exception){
        ApiResult.Error(e.localizedMessage ?: "Network error")
    }
}

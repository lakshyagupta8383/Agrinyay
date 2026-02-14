package com.example.agrinyay.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object BackendService {

    private const val BASE_URL = "http://YOUR_BACKEND_URL/"

    val api: BackendApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(BackendApi::class.java)
    }
}

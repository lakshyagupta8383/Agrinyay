package com.example.agrinyay.data.remote
import com.example.agrinyay.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private const val BASE_URL=BuildConfig.BASE_URL

    private val logging=HttpLoggingInterceptor().apply{
        level=HttpLoggingInterceptor.Level.BODY
    }

    private val client=OkHttpClient.Builder()
        .addInterceptor(logging)
        .build()

    val api:BackendApi by lazy{
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(BackendApi::class.java)
    }
}

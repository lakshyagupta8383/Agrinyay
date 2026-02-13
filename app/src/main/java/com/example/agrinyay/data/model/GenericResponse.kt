package com.example.agrinyay.data.model

data class GenericResponse<T>(
    val success:Boolean,
    val data:T
)

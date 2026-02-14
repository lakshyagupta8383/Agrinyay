package com.example.agrinyay.data.model

data class VehicleDashboard(
    val hardwareId:String,
    val currentTemp:Double,
    val currentHumidity:Double,
    val batches:List<BatchDashboard>
)

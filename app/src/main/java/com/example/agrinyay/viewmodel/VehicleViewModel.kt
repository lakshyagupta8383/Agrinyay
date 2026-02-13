package com.example.agrinyay.viewmodel

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import com.example.agrinyay.model.Vehicle

class VehicleViewModel:ViewModel(){

    var vehicleList by mutableStateOf<List<Vehicle>>(emptyList())
        private set

    fun addVehicle(hardwareId:String){
        val vehicle=Vehicle(hardwareId)
        vehicleList=vehicleList+vehicle

        // TODO: Call backend API here
        // send farmerId + hardwareId
    }
}

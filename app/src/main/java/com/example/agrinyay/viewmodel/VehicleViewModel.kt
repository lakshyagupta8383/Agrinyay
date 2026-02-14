package com.example.agrinyay.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.agrinyay.data.local.HardwareDataStore
import com.example.agrinyay.model.Vehicle
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class VehicleViewModel(application: Application) : AndroidViewModel(application) {

    private val dataStore = HardwareDataStore(application.applicationContext)

    private val _vehicleList = MutableStateFlow<List<Vehicle>>(emptyList())
    val vehicleList: StateFlow<List<Vehicle>> = _vehicleList

    private val _selectedHardwareId = MutableStateFlow<String?>(null)
    val selectedHardwareId: StateFlow<String?> = _selectedHardwareId

    init {
        loadHardware()
    }

    private fun loadHardware() {
        viewModelScope.launch {
            dataStore.getHardwareIds().collect { ids ->
                _vehicleList.value = ids.map { Vehicle(it) }
            }
        }
    }

    fun addVehicle(hardwareId: String) {
        viewModelScope.launch {
            dataStore.saveHardwareId(hardwareId)
        }
        _selectedHardwareId.value = hardwareId
    }

    fun selectHardware(hardwareId: String) {
        _selectedHardwareId.value = hardwareId
    }

    fun getCurrentHardwareId(): String? {
        return _selectedHardwareId.value
    }
}

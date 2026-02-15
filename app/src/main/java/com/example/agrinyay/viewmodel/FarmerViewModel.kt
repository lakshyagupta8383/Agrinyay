package com.example.agrinyay.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.agrinyay.data.model.DashboardResponse
import com.example.agrinyay.repository.DashboardRepository
import com.example.agrinyay.utils.ApiResult
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FarmerViewModel : ViewModel() {

    private val repository = DashboardRepository()

    private val _dashboardState: MutableStateFlow<ApiResult<DashboardResponse>> =
        MutableStateFlow(ApiResult.Loading)

    val dashboardState: StateFlow<ApiResult<DashboardResponse>> =
        _dashboardState

    private var pollingJob: Job? = null

    fun startDashboardUpdates(farmerId: String) {

        if (pollingJob != null) return

        pollingJob = viewModelScope.launch {
            while (true) {
                _dashboardState.value = ApiResult.Loading
                _dashboardState.value = repository.getDashboard(farmerId)
                delay(8000)
            }
        }
    }

    fun stopDashboardUpdates() {
        pollingJob?.cancel()
        pollingJob = null
    }

    override fun onCleared() {
        super.onCleared()
        stopDashboardUpdates()
    }
}
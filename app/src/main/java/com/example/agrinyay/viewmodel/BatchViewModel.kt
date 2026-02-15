package com.example.agrinyay.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.agrinyay.data.model.*
import com.example.agrinyay.repository.AgriRepository
import com.example.agrinyay.utils.ApiResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class BatchViewModel : ViewModel() {
    private val repository = AgriRepository()
    private val auth = FirebaseAuth.getInstance()

    private val _batches = MutableStateFlow<List<BatchData>>(emptyList())
    val batches: StateFlow<List<BatchData>> = _batches

    private val _cratesMap = MutableStateFlow<Map<String, List<Crate>>>(emptyMap())
    val cratesMap: StateFlow<Map<String, List<Crate>>> = _cratesMap

    private val _batchState = MutableStateFlow<ApiResult<BatchData>?>(null)
    val batchState: StateFlow<ApiResult<BatchData>?> = _batchState

    fun fetchBatches(farmerId: String) {
        viewModelScope.launch {
            try {
                val token = auth.currentUser?.getIdToken(true)?.await()?.token
                if (token != null) {
                    val result = repository.getBatches("Bearer $token", farmerId)
                    if (result is ApiResult.Success) {
                        _batches.value = result.data
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun createBatch(hardware: String, cropType: String, quantity: Int, farmerId: String) {
        val request = CreateBatchRequest(cropType, quantity, hardware)
        viewModelScope.launch {
            try {
                val token = auth.currentUser?.getIdToken(true)?.await()?.token
                if (token != null) {
                    val result = repository.createBatch("Bearer $token", request)
                    _batchState.value = result
                    if (result is ApiResult.Success) {
                        fetchBatches(farmerId)
                    }
                }
            } catch (e: Exception) {
                _batchState.value = ApiResult.Error(e.message ?: "Error")
            }
        }
    }

    fun attachCrate(batchId: String, qrCode: String, condition: String) {
        val request = AttachCrateRequest(qrCode, condition, batchId)
        viewModelScope.launch {
            try {
                val token = auth.currentUser?.getIdToken(true)?.await()?.token
                if (token != null) {
                    val result = repository.attachCrate("Bearer $token", batchId, request)

                    if (result is ApiResult.Success) {
                        val currentMap = _cratesMap.value.toMutableMap()
                        val currentList = currentMap[batchId] ?: emptyList()
                        val newCrate = Crate(qrCode, condition)
                        currentMap[batchId] = currentList + newCrate
                        _cratesMap.value = currentMap
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // --- UPDATED FUNCTION ---
    fun fetchCrates(batchId: String) {
        viewModelScope.launch {
            try {
                val token = auth.currentUser?.getIdToken(true)?.await()?.token
                if (token != null) {
                    val result = repository.getCrates("Bearer $token", batchId)

                    // Fix for type casting issues
                    if (result is ApiResult.Success<*>) {
                        val responseData = (result as ApiResult.Success<GetCratesResponse>).data
                        val currentMap = _cratesMap.value.toMutableMap()
                        currentMap[batchId] = responseData.crates
                        _cratesMap.value = currentMap
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
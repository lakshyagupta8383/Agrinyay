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

    private val _crates = MutableStateFlow<List<AttachCrateRequest>>(emptyList())
    val crates: StateFlow<List<AttachCrateRequest>> = _crates

    private val _batchState = MutableStateFlow<ApiResult<BatchData>?>(null)
    val batchState: StateFlow<ApiResult<BatchData>?> = _batchState

    fun fetchBatches(farmerId: String) {
        viewModelScope.launch {
            try {
                // FIX: Get Token First!
                val token = auth.currentUser?.getIdToken(true)?.await()?.token

                if (token != null) {
                    val bearerToken = "Bearer $token"
                    val result = repository.getBatches(bearerToken, farmerId)
                    if (result is ApiResult.Success) {
                        _batches.value = result.data
                    }
                }
            } catch (e: Exception) {
                // Handle error
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

                    // Refresh list on success
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
                        _crates.value = _crates.value + request
                    }
                }
            } catch (e: Exception) { /* Silent fail */ }
        }
    }
}
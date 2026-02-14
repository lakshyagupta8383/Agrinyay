package com.example.agrinyay.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.agrinyay.data.model.CreateBatchRequest
import com.example.agrinyay.data.model.CreateBatchResponse
import com.example.agrinyay.data.model.AttachCrateRequest
import com.example.agrinyay.repository.AgriRepository
import com.example.agrinyay.utils.ApiResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class BatchViewModel : ViewModel() {

    private val repository = AgriRepository()

    // ----------------------------
    // LOCAL UI LISTS
    // ----------------------------

    private val _batches =
        MutableStateFlow(emptyList<CreateBatchResponse>())
    val batches: StateFlow<List<CreateBatchResponse>> = _batches

    private val _crates =
        MutableStateFlow(emptyList<AttachCrateRequest>())
    val crates: StateFlow<List<AttachCrateRequest>> = _crates

    // ----------------------------
    // API STATES
    // ----------------------------

    private val _batchState =
        MutableStateFlow<ApiResult<CreateBatchResponse>?>(null)
    val batchState: StateFlow<ApiResult<CreateBatchResponse>?> = _batchState

    private val _crateState =
        MutableStateFlow<ApiResult<Unit>?>(null)
    val crateState: StateFlow<ApiResult<Unit>?> = _crateState

    var latestBatchId: String? = null
        private set

    // ----------------------------
    // CREATE BATCH
    // ----------------------------

    fun createBatch(
        farmerUid: String,
        vehicleId: String,
        cropType: String,
        quantity: Int,
        originLocation: String
    ) {

        val request = CreateBatchRequest(
            farmerUid = farmerUid,
            vehicleId = vehicleId,
            cropType = cropType,
            cropQuantityKg = quantity,
            originLocation = originLocation
        )

        viewModelScope.launch {

            val result = repository.createBatch(request)

            _batchState.value = result

            if (result is ApiResult.Success) {
                latestBatchId = result.data.batchId
                _batches.value = _batches.value + result.data
            }
        }
    }

    // ----------------------------
    // ATTACH CRATE
    // ----------------------------

    fun attachCrate(
        batchId: String,
        qrCode: String,
        condition: String
    ) {

        val request = AttachCrateRequest(
            batchId = batchId,
            qrCode = qrCode,
            condition = condition
        )

        viewModelScope.launch {

            val result = repository.attachCrate(request)

            _crateState.value = result

            if (result is ApiResult.Success) {
                _crates.value = _crates.value + request
            }
        }
    }
}

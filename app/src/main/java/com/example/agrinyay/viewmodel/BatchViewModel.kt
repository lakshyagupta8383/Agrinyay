package com.example.agrinyay.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.agrinyay.data.model.CreateBatchRequest
import com.example.agrinyay.data.model.CreateBatchResponse
import com.example.agrinyay.data.model.AttachCrateRequest
import com.example.agrinyay.repository.AgriRepository
import com.example.agrinyay.utils.ApiResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class BatchViewModel : ViewModel() {

    private val repository = AgriRepository()

    private val _batches =
        MutableStateFlow(emptyList<CreateBatchResponse>())
    val batches: StateFlow<List<CreateBatchResponse>> = _batches

    private val _crates =
        MutableStateFlow(emptyList<AttachCrateRequest>())
    val crates: StateFlow<List<AttachCrateRequest>> = _crates

    private val _batchState =
        MutableStateFlow<ApiResult<CreateBatchResponse>?>(null)
    val batchState: StateFlow<ApiResult<CreateBatchResponse>?> = _batchState

    private val _crateState =
        MutableStateFlow<ApiResult<Unit>?>(null)
    val crateState: StateFlow<ApiResult<Unit>?> = _crateState

    var latestBatchId: String? = null
        private set

    fun createBatch(
        vehicleId: String,
        cropType: String,
        quantity: Int,
        originLocation: String
    ) {

        val request = CreateBatchRequest(
            vehicleId = vehicleId,
            cropType = cropType,
            cropQuantityKg = quantity,
            originLocation = originLocation
        )

        FirebaseAuth.getInstance().currentUser
            ?.getIdToken(true)
            ?.addOnSuccessListener { result ->

                val token = "Bearer ${result.token}"

                viewModelScope.launch {

                    val apiResult = repository.createBatch(
                        token = token,
                        request = request
                    )

                    _batchState.value = apiResult

                    if (apiResult is ApiResult.Success) {
                        latestBatchId = apiResult.data.batchId
                        _batches.value = _batches.value + apiResult.data
                    }
                }
            }
    }

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

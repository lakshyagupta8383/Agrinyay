package com.example.agrinyay.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.agrinyay.repository.BatchRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class BatchState{
    object Idle:BatchState()
    object Loading:BatchState()
    data class Success(val batchId:String):BatchState()
    data class Error(val message:String):BatchState()
}

class BatchViewModel:ViewModel(){

    private val repository=BatchRepository()

    private val _state=MutableStateFlow<BatchState>(BatchState.Idle)
    val state:StateFlow<BatchState> = _state

    fun createBatch(
        farmerId:String,
        crateId:String,
        crop:String,
        quantity:String
    ){
        viewModelScope.launch{
            _state.value=BatchState.Loading

            val result=repository.createBatch(
                farmerId,
                crateId,
                crop,
                quantity
            )

            result.onSuccess{
                _state.value=BatchState.Success(it)
            }.onFailure{
                _state.value=BatchState.Error(it.message ?: "Error")
            }
        }
    }
}

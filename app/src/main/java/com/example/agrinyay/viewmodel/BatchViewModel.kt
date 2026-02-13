package com.example.agrinyay.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.UUID

data class Batch(
    val batchId:String,
    val farmerId:String,
    val hardwareId:String,
    val fruitType:String,
    val weight:String,
    val location:String
)

data class Crate(
    val crateId:String,
    val batchId:String,
    val condition:String,
    val timestamp:String
)

class BatchViewModel:ViewModel(){

    private val _batches=MutableStateFlow<List<Batch>>(emptyList())
    val batches:StateFlow<List<Batch>> = _batches

    private val _crates=MutableStateFlow<List<Crate>>(emptyList())
    val crates:StateFlow<List<Crate>> = _crates

    var latestBatchId:String=""

    fun createBatch(
        farmerId:String,
        hardwareId:String,
        fruitType:String,
        weight:String,
        location:String
    ){

        val newBatchId=UUID.randomUUID().toString()

        val batch=Batch(
            batchId=newBatchId,
            farmerId=farmerId,
            hardwareId=hardwareId,
            fruitType=fruitType,
            weight=weight,
            location=location
        )

        _batches.value=_batches.value+batch

        latestBatchId=newBatchId
    }

    fun addCrate(
        batchId:String,
        crateId:String,
        condition:String,
        timestamp:String
    ){

        val crate=Crate(
            crateId=crateId,
            batchId=batchId,
            condition=condition,
            timestamp=timestamp
        )

        _crates.value=_crates.value+crate
    }
}

package com.example.agrinyay.viewmodel

import androidx.lifecycle.ViewModel
import com.example.agrinyay.data.model.Crate
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.UUID

data class Batch(
    val batchId: String,
    val farmerId: String,
    val fruitType: String,
    val weight: String,
    val location: String
)

class BatchViewModel : ViewModel() {

    private val _batches = MutableStateFlow<List<Batch>>(emptyList())
    val batches: StateFlow<List<Batch>> = _batches

    private val _crates = MutableStateFlow<List<Crate>>(emptyList())
    val crates: StateFlow<List<Crate>> = _crates

    fun createBatch(
        farmerId: String,
        fruitType: String,
        weight: String,
        location: String
    ) {

        val batchId = UUID.randomUUID().toString()

        val newBatch = Batch(
            batchId = batchId,
            farmerId = farmerId,
            fruitType = fruitType,
            weight = weight,
            location = location
        )

        _batches.value = _batches.value + newBatch
    }

    fun addCrate(
        batchId: String,
        crateId: String,
        condition: String
    ) {

        val timestamp = System.currentTimeMillis()

        val newCrate = Crate(
            crateId = crateId,
            batchId = batchId,
            condition = condition,
            timestamp = timestamp
        )

        _crates.value = _crates.value + newCrate
    }

    fun loadBatches(farmerId: String) {}

    fun loadCrates(batchId: String) {}
}

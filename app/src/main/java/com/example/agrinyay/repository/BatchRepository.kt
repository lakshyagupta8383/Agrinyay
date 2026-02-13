package com.example.agrinyay.repository

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class BatchRepository {

    private val firestore = FirebaseFirestore.getInstance()

    suspend fun createBatch(
        farmerId: String,
        batchId: String,
        fruitType: String,
        weight: String,
        location: String
    ) {
        val batchData = mapOf(
            "farmerId" to farmerId,
            "fruitType" to fruitType,
            "weight" to weight,
            "location" to location
        )

        firestore.collection("batches")
            .document(batchId)
            .set(batchData)
            .await()
    }

    suspend fun getBatches(farmerId: String): List<Pair<String, Map<String, Any>>> {

        val snapshot = firestore.collection("batches")
            .whereEqualTo("farmerId", farmerId)
            .get()
            .await()

        return snapshot.documents.map { document ->
            Pair(document.id, document.data ?: emptyMap())
        }
    }


    suspend fun addCrate(
        batchId: String,
        crateId: String,
        condition: String,
        timestamp: Long
    ) {

        val crateData = mapOf(
            "crateId" to crateId,
            "condition" to condition,
            "timestamp" to timestamp
        )

        firestore.collection("batches")
            .document(batchId)
            .collection("crates")
            .document(crateId)
            .set(crateData)
            .await()
    }

    suspend fun getCrates(batchId: String): List<Map<String, Any>> {

        val snapshot = firestore.collection("batches")
            .document(batchId)
            .collection("crates")
            .get()
            .await()

        return snapshot.documents.map { it.data ?: emptyMap() }
    }
}

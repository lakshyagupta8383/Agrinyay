package com.example.agrinyay.data.remote

import com.example.agrinyay.data.model.*
import kotlinx.coroutines.delay
import java.util.UUID
import java.time.Instant

class MockBackendService:BackendService{

    // In-memory storage simulation
    private val batches=mutableListOf<CreateBatchResponse>()
    private val crates=mutableMapOf<String,String>()
    // qrCode -> batchId (simulating currentBatchId)

    override suspend fun createBatch(
        request:CreateBatchRequest
    ):GenericResponse<CreateBatchResponse>{

        delay(1000)

        val newBatch=CreateBatchResponse(
            id=UUID.randomUUID().toString(),
            vehicleId=request.vehicleId,
            cropType=request.cropType,
            cropQuantityKg=request.cropQuantityKg,
            originLocation=request.originLocation,
            status="CREATED",
            createdAt=Instant.now().toString()
        )

        batches.add(newBatch)

        return GenericResponse(
            success=true,
            data=newBatch
        )
    }


    override suspend fun attachCrate(
        request:AttachCrateRequest
    ):GenericResponse<Unit>{

        delay(800)

        // Check if crate already in use
        val existingBatchId=crates[request.qrCode]

        if(existingBatchId!=null && existingBatchId!=request.batchId){
            return GenericResponse(
                success=false,
                data=Unit
            )
        }

        // Assign crate to batch
        crates[request.qrCode]=request.batchId

        return GenericResponse(
            success=true,
            data=Unit
        )
    }
}

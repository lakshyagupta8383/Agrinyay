package com.example.agrinyay.ui.farmer

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import android.util.Log
import com.example.agrinyay.viewmodel.BatchViewModel

@Composable
fun MyBatchesScreen(
    navController: NavController,
    farmerId: String,
    viewModel: BatchViewModel
) {

    val batches by viewModel.batches.collectAsState()


    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate("create_batch/$farmerId")
                }
            ) {
                Text("+")
            }
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {

            Text(
                text = "My Batches",
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(Modifier.height(16.dp))

            if (batches.isEmpty()) {
                Text("No batches created yet")
            }

            LazyColumn {

                items(batches) { batch ->

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                            .clickable {

                                Log.d("DEBUG", "FarmerId: $farmerId")
                                Log.d("DEBUG", "BatchId: ${batch.batchId}")

                                if (batch.batchId.isNotBlank()) {
                                    navController.navigate(
                                        "batch_detail/$farmerId/${batch.batchId}"
                                    )
                                }
                            }
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text("Fruit: ${batch.fruitType}")
                            Text("Weight: ${batch.weight}")
                            Text("Location: ${batch.location}")
                        }
                    }
                }
            }
        }
    }
}

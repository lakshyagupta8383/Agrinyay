package com.example.agrinyay.ui.farmer

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.agrinyay.ui.components.AgriBackground
import com.example.agrinyay.viewmodel.BatchViewModel

@Composable
fun MyBatchesScreen(
    navController: NavController,
    farmerId: String,
    hardwareId: String,
    viewModel: BatchViewModel
) {

    val batches by viewModel.batches.collectAsState()

    val filteredBatches = batches.filter {
        it.hardwareId == hardwareId
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(
                        "create_batch/$farmerId/$hardwareId"
                    )
                }
            ) {
                Text("+")
            }
        }
    ) { padding ->

        AgriBackground {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(horizontal = 24.dp)
            ) {

                Spacer(Modifier.height(40.dp))

                Text(
                    text = "Batches 🌾",
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(Modifier.height(24.dp))

                if (filteredBatches.isEmpty()) {

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 80.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "No batches yet 🌱",
                            style = MaterialTheme.typography.titleLarge
                        )

                        Spacer(Modifier.height(8.dp))

                        Text(
                            text = "Create one using + button.",
                            color = Color.Gray
                        )
                    }

                } else {

                    LazyColumn {

                        items(filteredBatches) { batch ->

                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 10.dp)
                                    .clickable {

                                        Log.d("DEBUG", "BatchId: ${batch.batchId}")

                                        navController.navigate(
                                            "batch_detail/$farmerId/${batch.batchId}"
                                        )
                                    },
                                shape = RoundedCornerShape(24.dp),
                                elevation = CardDefaults.cardElevation(10.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = Color.White
                                )
                            ) {

                                Column(
                                    modifier = Modifier.padding(20.dp)
                                ) {

                                    Text(
                                        text = "🍎 ${batch.fruitType}",
                                        style = MaterialTheme.typography.titleLarge,
                                        color = MaterialTheme.colorScheme.primary
                                    )

                                    Spacer(Modifier.height(8.dp))

                                    Text("Weight: ${batch.weight} kg")

                                    Spacer(Modifier.height(4.dp))

                                    Text(
                                        text = "Location: ${batch.location}",
                                        color = Color.Gray
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

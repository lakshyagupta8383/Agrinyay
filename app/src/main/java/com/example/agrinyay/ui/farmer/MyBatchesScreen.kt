package com.example.agrinyay.ui.farmer

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.agrinyay.ui.components.AgriBackground
import com.example.agrinyay.viewmodel.BatchViewModel

@Composable
fun MyBatchesScreen(
    navController: NavController,
    farmerUid: String,
    vehicleId: String,
    viewModel: BatchViewModel
) {
    // Collect the list of batches from the ViewModel
    val batches by viewModel.batches.collectAsState()

    // Trigger a refresh when the screen loads
    LaunchedEffect(Unit) {
        viewModel.fetchBatches(farmerUid)
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate("manage_batches/$farmerUid")
                },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Batch", tint = Color.White)
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
                    text = "My Batches 🌾",
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(Modifier.height(24.dp))

                if (batches.isEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = androidx.compose.ui.Alignment.Center
                    ) {
                        Text(
                            text = "No batches yet 🌱",
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color.Gray
                        )
                    }
                } else {
                    LazyColumn(
                        contentPadding = PaddingValues(bottom = 80.dp)
                    ) {
                        items(batches) { batch ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 10.dp)
                                    .clickable {
                                        navController.navigate("batch_detail/${batch.batchId}")
                                    },
                                shape = RoundedCornerShape(24.dp),
                                elevation = CardDefaults.cardElevation(4.dp),
                                colors = CardDefaults.cardColors(containerColor = Color.White)
                            ) {
                                Column(
                                    modifier = Modifier.padding(20.dp)
                                ) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text(
                                            text = "🍎 ${batch.cropType}",
                                            style = MaterialTheme.typography.titleLarge,
                                            color = MaterialTheme.colorScheme.primary
                                        )

                                        Badge(
                                            containerColor = MaterialTheme.colorScheme.secondaryContainer
                                        ) {
                                            Text(
                                                text = batch.status,
                                                modifier = Modifier.padding(4.dp)
                                            )
                                        }
                                    }

                                    Spacer(Modifier.height(8.dp))

                                    // FIX 1: cropQuantityKg -> cropQuantity
                                    Text(
                                        text = "Weight: ${batch.cropQuantity} kg",
                                        style = MaterialTheme.typography.bodyMedium
                                    )

                                    // FIX 2: vehicleId -> hardware
                                    Text(
                                        text = "Vehicle: ${batch.hardware}",
                                        style = MaterialTheme.typography.bodySmall
                                    )

                                    Spacer(Modifier.height(4.dp))

                                    Text(
                                        text = "Created: ${batch.createdAt}",
                                        style = MaterialTheme.typography.bodySmall,
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
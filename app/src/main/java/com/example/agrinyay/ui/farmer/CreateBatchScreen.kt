package com.example.agrinyay.ui.farmer

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
fun CreateBatchScreen(
    navController: NavController,
    farmerId: String,
    viewModel: BatchViewModel
) {
    var inputHardware by remember { mutableStateOf("") }
    var fruitType by remember { mutableStateOf("") }
    var weight by remember { mutableStateOf("") }

    val batches by viewModel.batches.collectAsState()

    // Load initial list
    LaunchedEffect(farmerId) {
        viewModel.fetchBatches(farmerId)
    }

    Scaffold { padding ->
        AgriBackground {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(horizontal = 24.dp)
            ) {
                Spacer(Modifier.height(20.dp))
                Text("Manage Crops 🌾", style = MaterialTheme.typography.headlineLarge, color = MaterialTheme.colorScheme.primary)
                Spacer(Modifier.height(20.dp))

                // Creation Card
                Card(
                    shape = RoundedCornerShape(24.dp),
                    elevation = CardDefaults.cardElevation(8.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        OutlinedTextField(
                            value = inputHardware,
                            onValueChange = { inputHardware = it },
                            label = { Text("Hardware ID") },
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(Modifier.height(8.dp))
                        Row(Modifier.fillMaxWidth()) {
                            OutlinedTextField(
                                value = fruitType,
                                onValueChange = { fruitType = it },
                                label = { Text("Crop") },
                                modifier = Modifier.weight(1f)
                            )
                            Spacer(Modifier.width(8.dp))
                            OutlinedTextField(
                                value = weight,
                                onValueChange = { weight = it },
                                label = { Text("Kg") },
                                modifier = Modifier.weight(0.6f)
                            )
                        }
                        Spacer(Modifier.height(16.dp))
                        Button(
                            onClick = {
                                val quantityInt = weight.toIntOrNull()
                                if (inputHardware.isNotBlank() && fruitType.isNotBlank() && quantityInt != null) {
                                    viewModel.createBatch(inputHardware, fruitType, quantityInt, farmerId)
                                    // Reset fields after click
                                    inputHardware = ""; fruitType = ""; weight = ""
                                }
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) { Text("Create Batch") }
                    }
                }

                Spacer(Modifier.height(24.dp))
                Text("My Active Batches", style = MaterialTheme.typography.titleLarge)
                Spacer(Modifier.height(12.dp))

                // List View
                if (batches.isEmpty()) {
                    Box(Modifier.fillMaxWidth().padding(20.dp), contentAlignment = Alignment.Center) {
                        Text("No batches found.", color = Color.Gray)
                    }
                } else {
                    LazyColumn(contentPadding = PaddingValues(bottom = 20.dp)) {
                        items(batches) { batch ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 6.dp)
                                    .clickable { navController.navigate("batch_detail/${batch.batchId}") },
                                shape = RoundedCornerShape(16.dp),
                                colors = CardDefaults.cardColors(containerColor = Color(0xFFF1F8E9))
                            ) {
                                Row(modifier = Modifier.padding(16.dp).fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                    Column {
                                        Text(batch.cropType.uppercase(), style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.primary)
                                        Text("HW: ${batch.hardware}", style = MaterialTheme.typography.bodySmall)
                                    }
                                    Column(horizontalAlignment = Alignment.End) {
                                        Text("${batch.cropQuantity} Kg", style = MaterialTheme.typography.titleMedium)
                                        Text(batch.createdAt.take(10), style = MaterialTheme.typography.bodySmall, color = Color.Gray)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
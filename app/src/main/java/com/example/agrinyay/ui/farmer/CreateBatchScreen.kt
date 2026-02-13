package com.example.agrinyay.ui.farmer

import androidx.compose.foundation.layout.*
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
    farmerUid: String,
    vehicleId: String,
    viewModel: BatchViewModel
) {

    var fruitType by remember { mutableStateOf("") }
    var weight by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }

    Scaffold { padding ->

        AgriBackground {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(horizontal = 24.dp)
            ) {

                Spacer(Modifier.height(40.dp))

                Text(
                    text = "Create New Batch 🌾",
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(Modifier.height(32.dp))

                Card(
                    shape = RoundedCornerShape(28.dp),
                    elevation = CardDefaults.cardElevation(8.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    modifier = Modifier.fillMaxWidth()
                ) {

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        OutlinedTextField(
                            value = fruitType,
                            onValueChange = { fruitType = it },
                            label = { Text("Fruit Type") },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true
                        )

                        Spacer(Modifier.height(20.dp))

                        OutlinedTextField(
                            value = weight,
                            onValueChange = { weight = it },
                            label = { Text("Weight (kg)") },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true
                        )

                        Spacer(Modifier.height(20.dp))

                        OutlinedTextField(
                            value = location,
                            onValueChange = { location = it },
                            label = { Text("Origin Location") },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true
                        )

                        Spacer(Modifier.height(32.dp))

                        Button(
                            onClick = {

                                if (
                                    fruitType.isNotBlank() &&
                                    weight.isNotBlank() &&
                                    location.isNotBlank()
                                ) {

                                    viewModel.createBatch(
                                        farmerUid = farmerUid,
                                        vehicleId = vehicleId,
                                        cropType = fruitType,
                                        quantity = weight.toInt(),
                                        originLocation = location
                                    )

                                    val newBatchId = viewModel.latestBatchId

                                    if (!newBatchId.isNullOrBlank()) {
                                        navController.navigate(
                                            "batch_detail/$newBatchId"
                                        )
                                    }
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(54.dp),
                            shape = RoundedCornerShape(20.dp)
                        ) {
                            Text("Save Batch")
                        }
                    }
                }
            }
        }
    }
}

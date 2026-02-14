package com.example.agrinyay.ui.farmer

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
)
 {

    val batches by viewModel.batches.collectAsState()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(
                        "create_batch/$farmerUid/$vehicleId"
                    )
                }
            )
            {
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

                if (batches.isEmpty()) {

                    Text("No batches yet 🌱")

                } else {

                    LazyColumn {

                        items(batches) { batch ->

                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 10.dp)
                                    .clickable {
                                        navController.navigate(
                                            "batch_detail/${batch.batchId}"
                                        )
                                    },
                                shape = RoundedCornerShape(24.dp),
                                elevation = CardDefaults.cardElevation(10.dp),
                                colors = CardDefaults.cardColors(containerColor = Color.White)
                            ) {

                                Column(
                                    modifier = Modifier.padding(20.dp)
                                ) {

                                    Text(
                                        text = "🍎 ${batch.cropType}",
                                        style = MaterialTheme.typography.titleLarge,
                                        color = MaterialTheme.colorScheme.primary
                                    )

                                    Spacer(Modifier.height(8.dp))

                                    Text("Weight: ${batch.cropQuantityKg} kg")

                                    Spacer(Modifier.height(4.dp))

                                    Text(
                                        text = "Origin: ${batch.originLocation}",
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

package com.example.agrinyay.ui.farmer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
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
fun BatchDetailScreen(
    navController: NavController,
    batchId: String,
    viewModel: BatchViewModel
) {
    // 1. Trigger Fetch when screen opens
    // This gets the saved crates from the backend (aligning with getCratesByBatch)
    LaunchedEffect(batchId) {
        viewModel.fetchCrates(batchId)
    }

    // 2. Observe the Map
    val cratesMap by viewModel.cratesMap.collectAsState()

    // 3. Extract ONLY the crates for this specific Batch ID
    // If the map has no entry for this batch yet, default to empty list
    val crates = cratesMap[batchId] ?: emptyList()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    // Navigate to scanner with batchId
                    navController.navigate("scan_attach/$batchId")
                },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = Color.White
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Crate"
                )
            }
        }
    ) { padding ->
        AgriBackground {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(24.dp)
            ) {
                Spacer(Modifier.height(40.dp))

                Text(
                    text = "Batch Details 📦",
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.primary
                )

                Text(
                    text = "Batch ID: $batchId",
                    style = MaterialTheme.typography.labelMedium,
                    color = Color.Gray
                )

                Spacer(Modifier.height(24.dp))

                if (crates.isEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "No crates added yet 🌱",
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color.Gray
                        )
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(crates) { crate ->
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(16.dp),
                                elevation = CardDefaults.cardElevation(2.dp),
                                colors = CardDefaults.cardColors(containerColor = Color.White)
                            ) {
                                Column(
                                    modifier = Modifier.padding(16.dp)
                                ) {
                                    Text(
                                        text = "Crate QR: ${crate.qrCode}",
                                        style = MaterialTheme.typography.titleMedium,
                                        color = MaterialTheme.colorScheme.secondary
                                    )

                                    Spacer(Modifier.height(4.dp))

                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text(
                                            text = "Condition: ",
                                            style = MaterialTheme.typography.bodyMedium,
                                            color = Color.DarkGray
                                        )
                                        Text(
                                            text = crate.condition,
                                            style = MaterialTheme.typography.bodyMedium,
                                            color = if (crate.condition == "RAW") Color(0xFF4CAF50) else Color(0xFFFF9800), // Green for Raw, Orange otherwise
                                            modifier = Modifier.padding(start = 4.dp)
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
}
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
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun ScanResultScreen(
    navController: NavController,
    farmerId: String,
    batchId: String,
    crateId: String,
    viewModel: BatchViewModel
) {

    var condition by remember { mutableStateOf("Raw") }

    Scaffold { padding ->

        AgriBackground {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Spacer(Modifier.height(40.dp))

                Text(
                    text = "Attach Crate 📦",
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(Modifier.height(16.dp))

                Card(
                    shape = RoundedCornerShape(24.dp),
                    elevation = CardDefaults.cardElevation(8.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp)
                    ) {

                        Text(
                            text = "Crate ID",
                            color = Color.Gray
                        )

                        Spacer(Modifier.height(4.dp))

                        Text(
                            text = crateId,
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.primary
                        )

                        Spacer(Modifier.height(24.dp))

                        Text("Select Condition")

                        Spacer(Modifier.height(12.dp))

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {

                            listOf("Raw", "Moderate", "Ripe").forEach { state ->

                                FilterChip(
                                    selected = condition == state,
                                    onClick = { condition = state },
                                    label = { Text(state) }
                                )
                            }
                        }

                        Spacer(Modifier.height(32.dp))

                        Button(
                            onClick = {

                                val timestamp = SimpleDateFormat(
                                    "dd-MM-yyyy HH:mm:ss",
                                    Locale.getDefault()
                                ).format(Date())

                                viewModel.addCrate(
                                    batchId = batchId,
                                    crateId = crateId,
                                    condition = condition,
                                    timestamp = timestamp
                                )

                                navController.popBackStack()
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(54.dp),
                            shape = RoundedCornerShape(20.dp)
                        ) {
                            Text("Save Crate")
                        }
                    }
                }
            }
        }
    }
}

package com.example.agrinyay.ui.farmer

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.agrinyay.viewmodel.VehicleViewModel

@Composable
fun VehiclesScreen(
    navController: NavController,
    farmerId: String,
    viewModel: VehicleViewModel
) {

    val vehicleList by viewModel.vehicleList.collectAsState()
    val selectedHardwareId by viewModel.selectedHardwareId.collectAsState()

    var inputHardwareId by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = "Create Hardware",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = inputHardwareId,
            onValueChange = { inputHardwareId = it },
            label = { Text("Enter Hardware ID") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                if (inputHardwareId.isNotBlank()) {
                    viewModel.addVehicle(inputHardwareId.trim())
                    inputHardwareId = ""
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Add Hardware")
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Available Hardware",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(12.dp))

        if (vehicleList.isEmpty()) {
            Text("No hardware added yet.")
        } else {
            LazyColumn {
                items(vehicleList) { vehicle ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {

                            Text(text = "Hardware ID: ${vehicle.hardwareId}")

                            if (vehicle.hardwareId == selectedHardwareId) {
                                Text("Selected")
                            }

                            Spacer(modifier = Modifier.height(6.dp))

                            Button(
                                onClick = {
                                    // 1️⃣ Save selection
                                    viewModel.selectHardware(vehicle.hardwareId)

                                    // 2️⃣ Navigate to Create Batch screen
                                    navController.navigate(
                                        "create_batch/$farmerId/${vehicle.hardwareId}"
                                    )
                                }
                            ) {
                                Text("Select")
                            }
                        }
                    }
                }
            }
        }
    }
}

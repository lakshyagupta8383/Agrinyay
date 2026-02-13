package com.example.agrinyay.ui.farmer

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.agrinyay.viewmodel.VehicleViewModel
import com.example.agrinyay.model.Vehicle

@Composable
fun VehiclesScreen(
    navController: NavController,
    farmerId: String,
    viewModel: VehicleViewModel
) {

    val vehicles = viewModel.vehicleList

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate("create_vehicle/$farmerId")
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
                text = "Your Vehicles 🚛",
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(Modifier.height(16.dp))

            if (vehicles.isEmpty()) {
                Text(
                    text = "No vehicles added yet.",
                    style = MaterialTheme.typography.bodyLarge
                )
            } else {

                LazyColumn {
                    items(vehicles) { vehicle: Vehicle ->

                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 6.dp)
                                .clickable {
                                    navController.navigate(
                                        "my_batches/$farmerId/${vehicle.hardwareId}"
                                    )
                                }
                        ) {
                            Text(
                                text = vehicle.hardwareId,
                                modifier = Modifier.padding(16.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

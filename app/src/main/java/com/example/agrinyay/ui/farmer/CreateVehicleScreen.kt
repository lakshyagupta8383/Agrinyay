package com.example.agrinyay.ui.farmer

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.agrinyay.viewmodel.VehicleViewModel

@Composable
fun CreateVehicleScreen(
    navController: NavController,
    farmerId: String,
    viewModel: VehicleViewModel
) {

    var hardwareId by remember { mutableStateOf("") }

    Scaffold { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp)
        ) {

            Spacer(Modifier.height(40.dp))

            Text(
                text = "Add Vehicle 🚛",
                style = MaterialTheme.typography.headlineLarge
            )

            Spacer(Modifier.height(32.dp))

            OutlinedTextField(
                value = hardwareId,
                onValueChange = { hardwareId = it },
                label = { Text("Enter Hardware ID") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = RoundedCornerShape(16.dp)
            )

            Spacer(Modifier.height(32.dp))

            Button(
                onClick = {
                    if (hardwareId.isNotBlank()) {
                        viewModel.addVehicle(hardwareId)
                        navController.popBackStack()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp),
                shape = RoundedCornerShape(20.dp)
            ) {
                Text("Save Vehicle")
            }
        }
    }
}

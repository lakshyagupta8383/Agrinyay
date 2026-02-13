package com.example.agrinyay.ui.farmer

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.agrinyay.viewmodel.BatchViewModel
@Composable
fun ScanResultScreen(
    navController: NavController,
    farmerId: String,
    batchId: String,
    crateId: String,
    viewModel: BatchViewModel
)

 {

    var condition by remember { mutableStateOf("Raw") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text("Crate ID: $crateId")

        Spacer(Modifier.height(16.dp))

        Text("Select Condition")

        Spacer(Modifier.height(8.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {

            listOf("Raw", "Moderate", "Ripe").forEach {
                FilterChip(
                    selected = condition == it,
                    onClick = { condition = it },
                    label = { Text(it) }
                )
            }
        }

        Spacer(Modifier.height(24.dp))

        Button(
            onClick = {
                viewModel.addCrate(
                    batchId = batchId,
                    crateId = crateId,
                    condition = condition
                )

                navController.popBackStack()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Save Crate")
        }

    }
}

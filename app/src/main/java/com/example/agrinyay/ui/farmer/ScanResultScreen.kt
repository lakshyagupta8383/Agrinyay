package com.example.agrinyay.ui.farmer

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.agrinyay.viewmodel.BatchViewModel

@Composable
fun ScanResultScreen(
    navController: NavController,
    batchId: String,
    crateId: String,
    viewModel: BatchViewModel
) {

    var condition by remember { mutableStateOf("RAW") }

    Scaffold { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp)
        ) {

            Spacer(Modifier.height(40.dp))

            Text("Attach Crate 📦", style = MaterialTheme.typography.headlineLarge)

            Spacer(Modifier.height(24.dp))

            Text("Crate QR: $crateId")

            Spacer(Modifier.height(24.dp))

            listOf("RAW", "SEMI_RIPPED", "RIPPED").forEach { state ->

                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {

                    RadioButton(
                        selected = condition == state,
                        onClick = { condition = state }
                    )

                    Spacer(Modifier.width(8.dp))

                    Text(state)
                }
            }

            Spacer(Modifier.height(32.dp))

            Button(
                onClick = {
                    viewModel.attachCrate(
                        batchId = batchId,
                        qrCode = crateId,
                        condition = condition
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

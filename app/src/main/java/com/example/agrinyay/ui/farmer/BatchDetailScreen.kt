package com.example.agrinyay.ui.farmer

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
import com.example.agrinyay.viewmodel.BatchViewModel

@Composable
fun BatchDetailScreen(
    navController: NavController,
    batchId: String,
    viewModel: BatchViewModel
)
 {

    val allCrates by viewModel.crates.collectAsState()

    val crates = allCrates.filter { it.batchId == batchId }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate("scan_attach/$batchId")
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
                .padding(24.dp)
        ) {

            Spacer(Modifier.height(40.dp))

            Text(
                text = "Batch Details 📦",
                style = MaterialTheme.typography.headlineLarge
            )

            Spacer(Modifier.height(32.dp))

            if (crates.isEmpty()) {
                Text("No crates added yet 🌱")
            } else {
                LazyColumn {
                    items(crates) { crate ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 10.dp),
                            shape = RoundedCornerShape(24.dp)
                        ) {

                            Column(
                                modifier = Modifier.padding(20.dp)
                            ) {

                                Text(
                                    text = "Crate QR: ${crate.qrCode}",
                                    style = MaterialTheme.typography.titleMedium
                                )

                                Spacer(Modifier.height(6.dp))

                                Text("Condition: ${crate.condition}")
                            }
                        }
                    }
                }
            }
        }
    }
}

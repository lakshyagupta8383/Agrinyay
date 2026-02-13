package com.example.agrinyay.ui.farmer
import com.example.agrinyay.data.model.Crate
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.agrinyay.viewmodel.BatchViewModel

@Composable
fun BatchDetailScreen(
    navController: NavController,
    farmerId: String,
    batchId: String,
    viewModel: BatchViewModel
) {

    // Collect crates from ViewModel
    val allCrates by viewModel.crates.collectAsState()

    // Filter crates belonging to this batch
    val crates = remember(allCrates, batchId) {
        allCrates.filter { it.batchId == batchId }
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate("scan_attach/$farmerId/$batchId")
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
                text = "Crates",
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (crates.isEmpty()) {
                Text("No crates added yet.")
            } else {
                LazyColumn {
                    items(crates) { crate ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp)
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp)
                            ) {
                                Text(text = "Crate ID: ${crate.crateId}")
                                Text(text = "Condition: ${crate.condition}")
                                Text(text = "Timestamp: ${crate.timestamp}")
                            }
                        }
                    }
                }
            }
        }
    }
}

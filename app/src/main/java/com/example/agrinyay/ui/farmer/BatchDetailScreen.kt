package com.example.agrinyay.ui.farmer
import com.example.agrinyay.data.model.Crate
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
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

    val allCrates by viewModel.crates.collectAsState()

    val crates = remember(allCrates, batchId) {
        allCrates.filter { it.batchId == batchId }
    }

    val gradient = Brush.verticalGradient(
        colors = listOf(
            Color(0xFFE8F5E9),
            Color(0xFFF1F8E9),
            Color.White
        )
    )

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate("scan_attach/$farmerId/$batchId")
                },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Text("+", color = Color.White)
            }
        }
    ) { padding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(gradient)
                .padding(padding)
        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp)
            ) {

                Spacer(Modifier.height(40.dp))

                Text(
                    text = "Batch Details 📦",
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(Modifier.height(6.dp))

                Text(
                    text = "Batch ID: $batchId",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )

                Spacer(Modifier.height(32.dp))

                if (crates.isEmpty()) {
                    Text(
                        text = "No crates added yet 🌱",
                        style = MaterialTheme.typography.titleMedium
                    )
                } else {
                    LazyColumn {
                        items(crates) { crate ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 10.dp),
                                shape = RoundedCornerShape(24.dp),
                                elevation = CardDefaults.cardElevation(10.dp),
                                colors = CardDefaults.cardColors(containerColor = Color.White)
                            ) {

                                Column(
                                    modifier = Modifier.padding(20.dp)
                                ) {

                                    Text(
                                        text = "Crate ID: ${crate.crateId}",
                                        style = MaterialTheme.typography.titleMedium,
                                        color = MaterialTheme.colorScheme.primary
                                    )

                                    Spacer(Modifier.height(6.dp))

                                    Text("Condition: ${crate.condition}")
                                    Text("Timestamp: ${crate.timestamp}")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

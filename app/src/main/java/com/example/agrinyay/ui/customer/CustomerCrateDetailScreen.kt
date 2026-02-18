package com.example.agrinyay.ui.customer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Gavel
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.agrinyay.ui.components.AgriBackground

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomerCrateDetailScreen(
    navController: NavController,
    qrCode: String
) {
    // --- MOCK DATA (We will replace this with API call later) ---
    val qualityScore = 92
    val farmerPrice = 1450
    val cropType = "Apples (Kashmir)"
    val weight = "20 KG"
    // ------------------------------------------------------------

    var myBid by remember { mutableStateOf("") }
    var showSuccessDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Crate Details") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        }
    ) { padding ->
        AgriBackground {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(24.dp)
            ) {
                // 1. Quality Card
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Column(Modifier.padding(20.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(cropType, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                                Text("QR: $qrCode", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
                            }
                            Badge(containerColor = Color(0xFF4CAF50)) { // Green Badge
                                Text("Grade A", modifier = Modifier.padding(6.dp), color = Color.White)
                            }
                        }

                        Spacer(Modifier.height(16.dp))
                        Divider()
                        Spacer(Modifier.height(16.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            DetailItem("Quality Score", "$qualityScore/100")
                            DetailItem("Weight", weight)
                        }
                    }
                }

                Spacer(Modifier.height(24.dp))

                // 2. Price Section
                Text("Pricing & Bidding", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.primary)
                Spacer(Modifier.height(12.dp))

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF8E1)) // Light Yellow
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.AttachMoney, null, tint = Color(0xFFF57C00))
                        Spacer(Modifier.width(12.dp))
                        Column {
                            Text("Farmer's Asking Price", style = MaterialTheme.typography.bodyMedium)
                            Text("₹ $farmerPrice", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
                        }
                    }
                }

                Spacer(Modifier.height(24.dp))

                // 3. Bidding Input
                OutlinedTextField(
                    value = myBid,
                    onValueChange = { myBid = it },
                    label = { Text("Your Offer (₹)") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    shape = RoundedCornerShape(12.dp),
                    leadingIcon = { Text("₹", style = MaterialTheme.typography.titleMedium) }
                )

                Spacer(Modifier.weight(1f))

                // 4. Action Button
                Button(
                    onClick = {
                        // Call Backend API to place bid
                        showSuccessDialog = true
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                    Icon(Icons.Default.Gavel, null)
                    Spacer(Modifier.width(8.dp))
                    Text("Place Bid", style = MaterialTheme.typography.titleMedium)
                }
            }
        }

        // Success Dialog
        if (showSuccessDialog) {
            AlertDialog(
                onDismissRequest = { },
                icon = { Icon(Icons.Default.CheckCircle, null, tint = Color(0xFF4CAF50)) },
                title = { Text("Bid Placed!") },
                text = { Text("Your offer of ₹$myBid has been sent to the farmer. You will be notified if they accept.") },
                confirmButton = {
                    Button(onClick = {
                        showSuccessDialog = false
                        navController.popBackStack() // Go back to Home
                    }) {
                        Text("Done")
                    }
                }
            )
        }
    }
}

@Composable
fun DetailItem(label: String, value: String) {
    Column {
        Text(label, style = MaterialTheme.typography.labelMedium, color = Color.Gray)
        Text(value, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
    }
}
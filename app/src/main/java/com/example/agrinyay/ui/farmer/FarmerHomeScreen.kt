package com.example.agrinyay.ui.farmer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Inventory
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.agrinyay.utils.ApiResult
import com.example.agrinyay.viewmodel.FarmerViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FarmerHomeScreen(
    navController: NavController,
    farmerId: String,
    viewModel: FarmerViewModel = viewModel()
) {

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var showLogoutDialog by remember { mutableStateOf(false) }

    val dashboardState by viewModel.dashboardState.collectAsState()

    // Start fetching live updates when screen opens
    LaunchedEffect(Unit) {
        viewModel.startDashboardUpdates(farmerId)
    }

    // Stop updates when screen closes
    DisposableEffect(Unit) {
        onDispose {
            viewModel.stopDashboardUpdates()
        }
    }

    val gradient = Brush.verticalGradient(
        colors = listOf(
            Color(0xFFE8F5E9),
            Color(0xFFF1F8E9),
            Color.White
        )
    )

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Spacer(Modifier.height(40.dp))

                NavigationDrawerItem(
                    label = { Text("Profile") },
                    selected = false,
                    onClick = { scope.launch { drawerState.close() } },
                    icon = { Icon(Icons.Default.Person, null) }
                )

                NavigationDrawerItem(
                    label = { Text("About App") },
                    selected = false,
                    onClick = { scope.launch { drawerState.close() } },
                    icon = { Icon(Icons.Default.Info, null) }
                )

                NavigationDrawerItem(
                    label = { Text("Logout") },
                    selected = false,
                    onClick = {
                        showLogoutDialog = true
                        scope.launch { drawerState.close() }
                    },
                    icon = { Icon(Icons.Default.Logout, null) }
                )
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("AgriNyay 🌾") },
                    navigationIcon = {
                        IconButton(onClick = {
                            scope.launch { drawerState.open() }
                        }) {
                            Icon(Icons.Default.Menu, contentDescription = "Menu")
                        }
                    }
                )
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

                    // --- UPDATED CARD FOR NEW FLOW ---
                    DashboardCard(
                        title = "LOAD CROPS",
                        subtitle = "Create & Manage Batches",
                        icon = Icons.Default.Inventory
                    ) {
                        // Takes user directly to the merged Create/List screen
                        navController.navigate("manage_batches/$farmerId")
                    }

                    Spacer(Modifier.height(32.dp))

                    Text(
                        text = "Live Vehicle Status",
                        style = MaterialTheme.typography.titleLarge
                    )

                    Spacer(Modifier.height(16.dp))

                    // --- LIVE DASHBOARD CONTENT ---
                    when (dashboardState) {
                        is ApiResult.Loading -> {
                            CircularProgressIndicator()
                        }
                        is ApiResult.Error -> {
                            Text("Failed to load dashboard", color = Color.Red)
                        }
                        is ApiResult.Success -> {
                            val data = (dashboardState as ApiResult.Success).data

                            if (data.vehicles.isEmpty()) {
                                Text("No active vehicles found.")
                            } else {
                                data.vehicles.forEach { vehicle ->
                                    Card(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(bottom = 16.dp),
                                        shape = RoundedCornerShape(20.dp),
                                        colors = CardDefaults.cardColors(containerColor = Color.White),
                                        elevation = CardDefaults.cardElevation(4.dp)
                                    ) {
                                        Column(
                                            modifier = Modifier.padding(16.dp)
                                        ) {
                                            Text(
                                                "Vehicle: ${vehicle.hardwareId}",
                                                style = MaterialTheme.typography.titleMedium,
                                                color = MaterialTheme.colorScheme.primary
                                            )

                                            Spacer(Modifier.height(6.dp))

                                            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                                                Text("🌡️ ${vehicle.currentTemp}°C")
                                                Text("💧 ${vehicle.currentHumidity}%")
                                            }

                                            Spacer(Modifier.height(12.dp))
                                            Divider()
                                            Spacer(Modifier.height(8.dp))

                                            vehicle.batches.forEach { batch ->
                                                Text(
                                                    "Batch: ${batch.batchId.takeLast(6)}...",
                                                    style = MaterialTheme.typography.bodyMedium,
                                                    color = Color.Gray
                                                )

                                                batch.crates.forEach { crate ->
                                                    val color = when {
                                                        crate.qualityScore >= 80 -> Color(0xFF2E7D32) // Green
                                                        crate.qualityScore >= 60 -> Color(0xFFF9A825) // Yellow
                                                        else -> Color.Red
                                                    }

                                                    Row(
                                                        modifier = Modifier.fillMaxWidth(),
                                                        horizontalArrangement = Arrangement.SpaceBetween
                                                    ) {
                                                        Text("• Crate ${crate.crateId}")
                                                        Text(
                                                            "Q: ${crate.qualityScore}",
                                                            color = color,
                                                            style = MaterialTheme.typography.labelLarge
                                                        )
                                                    }
                                                }
                                                Spacer(Modifier.height(8.dp))
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        if (showLogoutDialog) {
            AlertDialog(
                onDismissRequest = { showLogoutDialog = false },
                title = { Text("Logout") },
                text = { Text("Are you sure you want to logout?") },
                confirmButton = {
                    TextButton(
                        onClick = {
                            showLogoutDialog = false
                            FirebaseAuth.getInstance().signOut()
                            navController.navigate("login") {
                                popUpTo("splash") { inclusive = true }
                            }
                        }
                    ) { Text("Yes") }
                },
                dismissButton = {
                    TextButton(
                        onClick = { showLogoutDialog = false }
                    ) { Text("Cancel") }
                }
            )
        }
    }
}

@Composable
fun DashboardCard(
    title: String,
    subtitle: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        shape = RoundedCornerShape(28.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .background(
                        MaterialTheme.colorScheme.primary.copy(alpha = 0.12f),
                        CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            }

            Spacer(Modifier.width(20.dp))

            Column {
                Text(title, style = MaterialTheme.typography.titleLarge)
                Spacer(Modifier.height(6.dp))
                Text(subtitle, color = Color.Gray)
            }
        }
    }
}
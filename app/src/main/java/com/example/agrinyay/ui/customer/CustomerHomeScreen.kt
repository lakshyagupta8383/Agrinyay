package com.example.agrinyay.ui.customer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.agrinyay.utils.SessionManager
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomerHomeScreen(
    navController: NavController,
    customerId: String
) {
    // 1. Setup UI State
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var showLogoutDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current // Need context for SessionManager

    // 2. Background Gradient
    val gradient = Brush.verticalGradient(
        colors = listOf(
            Color(0xFFE8F5E9),
            Color(0xFFF1F8E9),
            Color.White
        )
    )

    // 3. Navigation Drawer (Side Menu)
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Spacer(Modifier.height(40.dp))

                Text(
                    "Buyer Profile",
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                Divider()

                NavigationDrawerItem(
                    label = { Text("My Profile") },
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

                // LOGOUT BUTTON
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
        // 4. Main Screen Content
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("AgriNyay Buyer 🛒") },
                    navigationIcon = {
                        IconButton(onClick = {
                            scope.launch { drawerState.open() }
                        }) {
                            Icon(Icons.Default.Menu, contentDescription = "Menu")
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Transparent // Blends with gradient
                    )
                )
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {
                        // Navigate to scanner (Logic coming next)
                        navController.navigate("customer_scan")
                    },
                    containerColor = MaterialTheme.colorScheme.tertiary, // Orange/Brown for Buyers
                    contentColor = Color.White
                ) {
                    Icon(Icons.Default.QrCodeScanner, contentDescription = "Scan to Buy")
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
                    Spacer(Modifier.height(20.dp))

                    // HEADER
                    Text(
                        text = "Find Fresh Produce 🌱",
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = "Fair prices, directly from farmers.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray
                    )

                    Spacer(Modifier.height(32.dp))

                    // STATS CARD
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF3E0)), // Light Orange
                        elevation = CardDefaults.cardElevation(4.dp)
                    ) {
                        Column(Modifier.padding(20.dp)) {
                            Text("Your Activity", style = MaterialTheme.typography.titleMedium)
                            Spacer(Modifier.height(8.dp))
                            Text(
                                "0 Active Bids",
                                style = MaterialTheme.typography.headlineSmall,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFFE65100)
                            )
                        }
                    }

                    Spacer(Modifier.height(24.dp))
                    Text("Recent Negotiations", style = MaterialTheme.typography.titleLarge)
                    Spacer(Modifier.height(16.dp))

                    // EMPTY STATE LIST
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .background(Color.White.copy(alpha = 0.6f), RoundedCornerShape(16.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("No negotiations yet 📉", color = Color.Gray)
                            Spacer(Modifier.height(8.dp))
                            Text(
                                "Scan a crate to start!",
                                color = MaterialTheme.colorScheme.primary,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }
            }
        }

        // 5. Logout Logic (The Professional Fix)
        if (showLogoutDialog) {
            AlertDialog(
                onDismissRequest = { showLogoutDialog = false },
                title = { Text("Logout") },
                text = { Text("Are you sure you want to logout?") },
                confirmButton = {
                    TextButton(
                        onClick = {
                            showLogoutDialog = false

                            // --- 1. Clear Local Session (The Fix) ---
                            val sessionManager = SessionManager(context)
                            sessionManager.logoutUser()

                            // --- 2. Sign out of Firebase ---
                            FirebaseAuth.getInstance().signOut()

                            // --- 3. Navigate to Login & Clear Stack ---
                            navController.navigate("login") {
                                popUpTo("customer_graph") { inclusive = true }
                                popUpTo("auth_graph") { inclusive = true }
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
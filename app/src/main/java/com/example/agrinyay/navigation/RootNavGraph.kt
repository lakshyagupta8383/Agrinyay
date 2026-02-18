package com.example.agrinyay.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.example.agrinyay.ui.auth.*
import com.example.agrinyay.ui.farmer.*
import com.example.agrinyay.ui.customer.* import com.example.agrinyay.viewmodel.BatchViewModel

@Composable
fun RootNavGraph() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "splash"
    ) {

        // --- SPLASH SCREEN ---
        composable("splash") {
            SplashScreen(navController)
        }

        // --- AUTH GRAPH (Login/Signup) ---
        navigation(
            startDestination = "login",
            route = "auth_graph"
        ) {
            composable("login") {
                LoginScreen(navController)
            }

            composable("signup") {
                SignupScreen(navController)
            }
        }

        // --- FARMER GRAPH ---
        navigation(
            startDestination = "farmer_home/{farmerId}",
            route = "farmer_graph"
        ) {

            // 1. Farmer Dashboard
            composable(
                route = "farmer_home/{farmerId}",
                arguments = listOf(
                    navArgument("farmerId") { type = NavType.StringType }
                )
            ) { backStackEntry ->
                val farmerId = backStackEntry.arguments?.getString("farmerId") ?: ""
                FarmerHomeScreen(navController, farmerId)
            }

            // 2. Manage Batches (Create/View List)
            composable(
                route = "manage_batches/{farmerId}",
                arguments = listOf(
                    navArgument("farmerId") { type = NavType.StringType }
                )
            ) { backStackEntry ->
                val farmerId = backStackEntry.arguments?.getString("farmerId") ?: ""
                val parentEntry = remember(backStackEntry) { navController.getBackStackEntry("farmer_graph") }
                val batchViewModel: BatchViewModel = viewModel(viewModelStoreOwner = parentEntry)

                CreateBatchScreen(navController, farmerId, batchViewModel)
            }

            // 3. Batch Details
            composable(
                route = "batch_detail/{batchId}",
                arguments = listOf(
                    navArgument("batchId") { type = NavType.StringType }
                )
            ) { backStackEntry ->
                val batchId = backStackEntry.arguments?.getString("batchId") ?: ""
                val parentEntry = remember(backStackEntry) { navController.getBackStackEntry("farmer_graph") }
                val batchViewModel: BatchViewModel = viewModel(viewModelStoreOwner = parentEntry)

                BatchDetailScreen(navController, batchId, batchViewModel)
            }

            // 4. Scan Attach (Adding a NEW Crate)
            composable(
                route = "scan_attach/{batchId}",
                arguments = listOf(
                    navArgument("batchId") { type = NavType.StringType }
                )
            ) { backStackEntry ->
                val batchId = backStackEntry.arguments?.getString("batchId") ?: ""
                val parentEntry = remember(backStackEntry) { navController.getBackStackEntry("farmer_graph") }
                val batchViewModel: BatchViewModel = viewModel(viewModelStoreOwner = parentEntry)

                ScanResultScreen(navController, batchId, "", batchViewModel)
            }

            // 5. Scan Result (Viewing/Checking an EXISTING Crate)
            composable(
                route = "scan_result/{batchId}/{crateId}",
                arguments = listOf(
                    navArgument("batchId") { type = NavType.StringType },
                    navArgument("crateId") { type = NavType.StringType }
                )
            ) { backStackEntry ->
                val batchId = backStackEntry.arguments?.getString("batchId") ?: ""
                val crateId = backStackEntry.arguments?.getString("crateId") ?: ""
                val parentEntry = remember(backStackEntry) { navController.getBackStackEntry("farmer_graph") }
                val batchViewModel: BatchViewModel = viewModel(viewModelStoreOwner = parentEntry)

                ScanResultScreen(navController, batchId, crateId, batchViewModel)
            }
        }

        // --- CUSTOMER GRAPH (Corrected Structure) ---
        navigation(
            startDestination = "customer_home/{customerId}",
            route = "customer_graph"
        ) {

            // 1. Customer Dashboard
            composable(
                route = "customer_home/{customerId}",
                arguments = listOf(navArgument("customerId") { type = NavType.StringType })
            ) { backStackEntry ->
                val customerId = backStackEntry.arguments?.getString("customerId") ?: ""
                CustomerHomeScreen(navController, customerId)
            }

            // 2. Customer Scanner
            composable("customer_scan") {
                CustomerScanScreen(navController)
            }

            // 3. Crate Details (The Bidding Screen)
            composable(
                route = "customer_crate_detail/{qrCode}",
                arguments = listOf(navArgument("qrCode") { type = NavType.StringType })
            ) { backStackEntry ->
                val qrCode = backStackEntry.arguments?.getString("qrCode") ?: ""
                CustomerCrateDetailScreen(navController, qrCode)
            }
        }
    }
}
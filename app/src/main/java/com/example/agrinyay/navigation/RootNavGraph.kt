package com.example.agrinyay.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.example.agrinyay.ui.auth.*
import com.example.agrinyay.ui.farmer.*
import com.example.agrinyay.viewmodel.BatchViewModel

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

                FarmerHomeScreen(
                    navController = navController,
                    farmerId = farmerId
                )
            }

            // 2. Manage Batches (Create/View List)
            composable(
                route = "manage_batches/{farmerId}",
                arguments = listOf(
                    navArgument("farmerId") { type = NavType.StringType }
                )
            ) { backStackEntry ->
                val farmerId = backStackEntry.arguments?.getString("farmerId") ?: ""

                // Get ViewModel scoped to the graph so data survives navigation
                val parentEntry = remember(backStackEntry) {
                    navController.getBackStackEntry("farmer_graph")
                }
                val batchViewModel: BatchViewModel = viewModel(viewModelStoreOwner = parentEntry)

                CreateBatchScreen(
                    navController = navController,
                    farmerId = farmerId,
                    viewModel = batchViewModel
                )
            }

            // 3. Batch Details
            composable(
                route = "batch_detail/{batchId}",
                arguments = listOf(
                    navArgument("batchId") { type = NavType.StringType }
                )
            ) { backStackEntry ->
                val batchId = backStackEntry.arguments?.getString("batchId") ?: ""

                val parentEntry = remember(backStackEntry) {
                    navController.getBackStackEntry("farmer_graph")
                }
                val batchViewModel: BatchViewModel = viewModel(viewModelStoreOwner = parentEntry)

                BatchDetailScreen(
                    navController = navController,
                    batchId = batchId,
                    viewModel = batchViewModel
                )
            }

            // 4. Scan Attach (Adding a NEW Crate)
            composable(
                route = "scan_attach/{batchId}",
                arguments = listOf(
                    navArgument("batchId") { type = NavType.StringType }
                )
            ) { backStackEntry ->
                val batchId = backStackEntry.arguments?.getString("batchId") ?: ""

                val parentEntry = remember(backStackEntry) {
                    navController.getBackStackEntry("farmer_graph")
                }
                val batchViewModel: BatchViewModel = viewModel(viewModelStoreOwner = parentEntry)

                // FIX: Use named arguments to prevent type mismatch
                ScanResultScreen(
                    navController = navController,
                    batchId = batchId,
                    crateId = "", // Pass empty string because we are creating a new one
                    viewModel = batchViewModel
                )
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

                val parentEntry = remember(backStackEntry) {
                    navController.getBackStackEntry("farmer_graph")
                }
                val batchViewModel: BatchViewModel = viewModel(viewModelStoreOwner = parentEntry)

                ScanResultScreen(
                    navController = navController,
                    batchId = batchId,
                    crateId = crateId, // Pass the existing ID
                    viewModel = batchViewModel
                )
            }
        }
    }
}
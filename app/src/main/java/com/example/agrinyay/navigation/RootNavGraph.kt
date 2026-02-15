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

        composable("splash") { SplashScreen(navController) }

        navigation(startDestination = "login", route = "auth_graph") {
            composable("login") { LoginScreen(navController) }
            composable("signup") { SignupScreen(navController) }
        }

        navigation(startDestination = "farmer_home/{farmerId}", route = "farmer_graph") {

            composable(
                route = "farmer_home/{farmerId}",
                arguments = listOf(navArgument("farmerId") { type = NavType.StringType })
            ) { backStackEntry ->
                val farmerId = backStackEntry.arguments?.getString("farmerId") ?: ""
                FarmerHomeScreen(navController, farmerId)
            }

            composable(
                route = "manage_batches/{farmerId}",
                arguments = listOf(navArgument("farmerId") { type = NavType.StringType })
            ) { backStackEntry ->
                val farmerId = backStackEntry.arguments?.getString("farmerId") ?: ""
                val parentEntry = remember(backStackEntry) { navController.getBackStackEntry("farmer_graph") }
                val batchViewModel: BatchViewModel = viewModel(viewModelStoreOwner = parentEntry)
                CreateBatchScreen(navController, farmerId, batchViewModel)
            }

            composable(
                route = "batch_detail/{batchId}",
                arguments = listOf(navArgument("batchId") { type = NavType.StringType })
            ) { backStackEntry ->
                val batchId = backStackEntry.arguments?.getString("batchId") ?: ""
                val parentEntry = remember(backStackEntry) { navController.getBackStackEntry("farmer_graph") }
                val batchViewModel: BatchViewModel = viewModel(viewModelStoreOwner = parentEntry)
                BatchDetailScreen(navController, batchId, batchViewModel)
            }

            // --- FIX FOR CRASH: Added missing route ---
            composable(
                route = "scan_attach/{batchId}",
                arguments = listOf(navArgument("batchId") { type = NavType.StringType })
            ) { backStackEntry ->
                val batchId = backStackEntry.arguments?.getString("batchId") ?: ""
                val parentEntry = remember(backStackEntry) { navController.getBackStackEntry("farmer_graph") }
                val batchViewModel: BatchViewModel = viewModel(viewModelStoreOwner = parentEntry)

                // FIX FOR BUILD ERROR: Pass empty string for crateId
                ScanResultScreen(
                    navController = navController,
                    batchId = batchId,
                    crateId = "",
                    viewModel = batchViewModel
                )
            }

            // --- Existing Scan Result Route ---
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

                ScanResultScreen(
                    navController = navController,
                    batchId = batchId,
                    crateId = crateId,
                    viewModel = batchViewModel
                )
            }
        }
    }
}
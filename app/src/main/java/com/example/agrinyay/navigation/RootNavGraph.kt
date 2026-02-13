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

        composable("splash") {
            SplashScreen(navController)
        }

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

        navigation(
            startDestination = "my_batches/{farmerId}",
            route = "farmer_graph"
        ) {

            composable(
                route = "my_batches/{farmerId}",
                arguments = listOf(
                    navArgument("farmerId") {
                        type = NavType.StringType
                    }
                )
            ) { backStackEntry ->

                val farmerId =
                    backStackEntry.arguments?.getString("farmerId") ?: ""

                val parentEntry = remember(backStackEntry) {
                    navController.getBackStackEntry("farmer_graph")
                }

                val batchViewModel: BatchViewModel =
                    viewModel(parentEntry)

                MyBatchesScreen(
                    navController = navController,
                    farmerId = farmerId,
                    viewModel = batchViewModel
                )
            }

            composable(
                route = "create_batch/{farmerId}",
                arguments = listOf(
                    navArgument("farmerId") {
                        type = NavType.StringType
                    }
                )
            ) { backStackEntry ->

                val farmerId =
                    backStackEntry.arguments?.getString("farmerId") ?: ""

                val parentEntry = remember(backStackEntry) {
                    navController.getBackStackEntry("farmer_graph")
                }

                val batchViewModel: BatchViewModel =
                    viewModel(parentEntry)

                CreateBatchScreen(
                    navController = navController,
                    farmerId = farmerId,
                    viewModel = batchViewModel
                )
            }

            composable(
                route = "batch_detail/{farmerId}/{batchId}",
                arguments = listOf(
                    navArgument("farmerId") { type = NavType.StringType },
                    navArgument("batchId") { type = NavType.StringType }
                )
            ) { backStackEntry ->

                val farmerId =
                    backStackEntry.arguments?.getString("farmerId") ?: ""

                val batchId =
                    backStackEntry.arguments?.getString("batchId") ?: ""

                val parentEntry = remember(backStackEntry) {
                    navController.getBackStackEntry("farmer_graph")
                }

                val batchViewModel: BatchViewModel =
                    viewModel(parentEntry)

                BatchDetailScreen(
                    navController = navController,
                    farmerId = farmerId,
                    batchId = batchId,
                    viewModel = batchViewModel
                )
            }

            composable(
                route = "scan_attach/{farmerId}/{batchId}",
                arguments = listOf(
                    navArgument("farmerId") { type = NavType.StringType },
                    navArgument("batchId") { type = NavType.StringType }
                )
            ) { backStackEntry ->

                val farmerId =
                    backStackEntry.arguments?.getString("farmerId") ?: ""

                val batchId =
                    backStackEntry.arguments?.getString("batchId") ?: ""

                val parentEntry = remember(backStackEntry) {
                    navController.getBackStackEntry("farmer_graph")
                }

                val batchViewModel: BatchViewModel =
                    viewModel(parentEntry)

                ScanAttachScreen(
                    navController = navController,
                    farmerId = farmerId,
                    batchId = batchId,
                    viewModel = batchViewModel
                )
            }

            composable(
                route = "scan_result/{farmerId}/{batchId}/{crateId}",
                arguments = listOf(
                    navArgument("farmerId") { type = NavType.StringType },
                    navArgument("batchId") { type = NavType.StringType },
                    navArgument("crateId") { type = NavType.StringType }
                )
            ) { backStackEntry ->

                val farmerId =
                    backStackEntry.arguments?.getString("farmerId") ?: ""

                val batchId =
                    backStackEntry.arguments?.getString("batchId") ?: ""

                val crateId =
                    backStackEntry.arguments?.getString("crateId") ?: ""

                val parentEntry = remember(backStackEntry) {
                    navController.getBackStackEntry("farmer_graph")
                }

                val batchViewModel: BatchViewModel =
                    viewModel(parentEntry)

                ScanResultScreen(
                    navController = navController,
                    farmerId = farmerId,
                    batchId = batchId,
                    crateId = crateId,   // ← THIS MUST EXIST
                    viewModel = batchViewModel
                )
            }

        }
    }
}

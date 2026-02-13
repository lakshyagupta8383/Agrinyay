package com.example.agrinyay.navigation
import com.example.agrinyay.viewmodel.VehicleViewModel

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
            startDestination = "farmer_home/{farmerId}",
            route = "farmer_graph"
        ) {
            composable(
                route = "create_vehicle/{farmerId}",
                arguments = listOf(
                    navArgument("farmerId") { type = NavType.StringType }
                )
            ) { backStackEntry ->

                val farmerId =
                    backStackEntry.arguments?.getString("farmerId") ?: ""

                val parentEntry = remember(backStackEntry) {
                    navController.getBackStackEntry("farmer_graph")
                }

                val vehicleViewModel: VehicleViewModel =
                    viewModel(parentEntry)

                CreateVehicleScreen(
                    navController = navController,
                    farmerId = farmerId,
                    viewModel = vehicleViewModel
                )

            }


            composable(
                route = "farmer_home/{farmerId}",
                arguments = listOf(
                    navArgument("farmerId") { type = NavType.StringType }
                )
            ) { backStackEntry ->

                val farmerId =
                    backStackEntry.arguments?.getString("farmerId") ?: ""

                FarmerHomeScreen(
                    navController = navController,
                    farmerId = farmerId
                )
            }

            composable(
                route = "vehicles/{farmerId}",
                arguments = listOf(
                    navArgument("farmerId") { type = NavType.StringType }
                )
            ) { backStackEntry ->

                val farmerId =
                    backStackEntry.arguments?.getString("farmerId") ?: ""

                val parentEntry = remember(backStackEntry) {
                    navController.getBackStackEntry("farmer_graph")
                }

                val vehicleViewModel: VehicleViewModel =
                    viewModel(parentEntry)

                VehiclesScreen(
                    navController = navController,
                    farmerId = farmerId,
                    viewModel = vehicleViewModel
                )

            }

            composable(
                route = "my_batches/{farmerId}/{hardwareId}",
                arguments = listOf(
                    navArgument("farmerId") { type = NavType.StringType },
                    navArgument("hardwareId") { type = NavType.StringType }
                )
            ) { backStackEntry ->

                val farmerId =
                    backStackEntry.arguments?.getString("farmerId") ?: ""

                val hardwareId =
                    backStackEntry.arguments?.getString("hardwareId") ?: ""

                val parentEntry = remember(backStackEntry) {
                    navController.getBackStackEntry("farmer_graph")
                }

                val batchViewModel: BatchViewModel =
                    viewModel(parentEntry)

                MyBatchesScreen(
                    navController = navController,
                    farmerId = farmerId,
                    hardwareId = hardwareId,
                    viewModel = batchViewModel
                )
            }


            composable(
                route = "create_batch/{farmerId}/{hardwareId}",
                arguments = listOf(
                    navArgument("farmerId") { type = NavType.StringType },
                    navArgument("hardwareId") { type = NavType.StringType }
                )
            ) { backStackEntry ->

                val farmerId =
                    backStackEntry.arguments?.getString("farmerId") ?: ""

                val hardwareId =
                    backStackEntry.arguments?.getString("hardwareId") ?: ""

                val parentEntry = remember(backStackEntry) {
                    navController.getBackStackEntry("farmer_graph")
                }

                val batchViewModel: BatchViewModel =
                    viewModel(parentEntry)

                CreateBatchScreen(
                    navController = navController,
                    farmerId = farmerId,
                    hardwareId = hardwareId,
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
                    crateId = crateId,
                    viewModel = batchViewModel
                )
            }
        }
    }
}

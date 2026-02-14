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
import com.example.agrinyay.viewmodel.VehicleViewModel

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
                    viewModel(viewModelStoreOwner = parentEntry)

                VehiclesScreen(
                    navController = navController,
                    farmerId = farmerId,
                    viewModel = vehicleViewModel
                )
            }

            composable(
                route = "create_batch/{farmerUid}/{vehicleId}",
                arguments = listOf(
                    navArgument("farmerUid") { type = NavType.StringType },
                    navArgument("vehicleId") { type = NavType.StringType }
                )
            ) { backStackEntry ->

                val farmerUid =
                    backStackEntry.arguments?.getString("farmerUid") ?: ""

                val vehicleId =
                    backStackEntry.arguments?.getString("vehicleId") ?: ""

                val parentEntry = remember(backStackEntry) {
                    navController.getBackStackEntry("farmer_graph")
                }

                val batchViewModel: BatchViewModel =
                    viewModel(viewModelStoreOwner = parentEntry)

                CreateBatchScreen(
                    navController = navController,
                    farmerUid = farmerUid,
                    vehicleId = vehicleId,
                    viewModel = batchViewModel
                )
            }

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
                    viewModel(viewModelStoreOwner = parentEntry)

                CreateVehicleScreen(
                    navController = navController,
                    farmerId = farmerId,
                    viewModel = vehicleViewModel
                )
            }

            composable(
                route = "batch_detail/{batchId}",
                arguments = listOf(
                    navArgument("batchId") { type = NavType.StringType }
                )
            ) { backStackEntry ->

                val batchId =
                    backStackEntry.arguments?.getString("batchId") ?: ""

                val parentEntry = remember(backStackEntry) {
                    navController.getBackStackEntry("farmer_graph")
                }

                val batchViewModel: BatchViewModel =
                    viewModel(viewModelStoreOwner = parentEntry)

                BatchDetailScreen(
                    navController = navController,
                    batchId = batchId,
                    viewModel = batchViewModel
                )
            }

            composable(
                route = "my_batches/{farmerUid}/{vehicleId}",
                arguments = listOf(
                    navArgument("farmerUid") { type = NavType.StringType },
                    navArgument("vehicleId") { type = NavType.StringType }
                )
            ) { backStackEntry ->

                val farmerUid =
                    backStackEntry.arguments?.getString("farmerUid") ?: ""

                val vehicleId =
                    backStackEntry.arguments?.getString("vehicleId") ?: ""

                val parentEntry = remember(backStackEntry) {
                    navController.getBackStackEntry("farmer_graph")
                }

                val batchViewModel: BatchViewModel =
                    viewModel(viewModelStoreOwner = parentEntry)

                MyBatchesScreen(
                    navController = navController,
                    farmerUid = farmerUid,
                    vehicleId = vehicleId,
                    viewModel = batchViewModel
                )
            }

            composable(
                route = "scan_result/{batchId}/{crateId}",
                arguments = listOf(
                    navArgument("batchId") { type = NavType.StringType },
                    navArgument("crateId") { type = NavType.StringType }
                )
            ) { backStackEntry ->

                val batchId =
                    backStackEntry.arguments?.getString("batchId") ?: ""

                val crateId =
                    backStackEntry.arguments?.getString("crateId") ?: ""

                val parentEntry = remember(backStackEntry) {
                    navController.getBackStackEntry("farmer_graph")
                }

                val batchViewModel: BatchViewModel =
                    viewModel(viewModelStoreOwner = parentEntry)

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

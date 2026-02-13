package com.example.agrinyay.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.example.agrinyay.ui.auth.LoginScreen
import com.example.agrinyay.ui.auth.SignupScreen
import com.example.agrinyay.ui.auth.SplashScreen
import com.example.agrinyay.ui.farmer.*

@Composable
fun RootNavGraph() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "splash"
    ) {

        // ---------------- Splash ----------------
        composable("splash") {
            SplashScreen(navController)
        }

        // ---------------- Auth Graph ----------------
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

        // ---------------- Farmer Graph ----------------
        navigation(
            startDestination = "farmer_home",
            route = "farmer_graph"
        ) {

            composable("farmer_home") {
                FarmerHomeScreen(navController)
            }

            composable("scan_attach") {
                ScanAttachScreen(navController)
            }

            composable(
                route = "create_batch/{crateId}",
                arguments = listOf(
                    navArgument("crateId") {
                        type = NavType.StringType
                    }
                )
            ) { backStackEntry ->

                val crateId =
                    backStackEntry.arguments?.getString("crateId") ?: ""

                CreateBatchScreen(
                    navController = navController,
                    crateId = crateId
                )
            }

            composable("my_batches") {
                MyBatchesScreen(navController)
            }
        }

        // ---------------- Batch Result ----------------
        composable(
            route = "batch_result/{batchId}",
            arguments = listOf(
                navArgument("batchId") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->

            val batchId =
                backStackEntry.arguments?.getString("batchId") ?: ""

            BatchResultScreen(batchId = batchId)
        }
    }
}

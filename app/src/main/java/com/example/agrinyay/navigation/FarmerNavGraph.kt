package com.example.agrinyay.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.agrinyay.ui.farmer.FarmerHomeScreen

fun NavGraphBuilder.FarmerNavGraph(navController:NavController){

    composable(
        route="farmer_home/{farmerId}",
        arguments=listOf(
            navArgument("farmerId"){ type=NavType.StringType }
        )
    ){ backStackEntry->

        val farmerId=
            backStackEntry.arguments?.getString("farmerId")?:""

        FarmerHomeScreen(
            navController=navController,
            farmerId=farmerId
        )
    }
}
package com.example.agrinyay.ui.auth
import androidx.compose.runtime.*
import androidx.navigation.NavController
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.agrinyay.viewmodel.AuthResult
import com.example.agrinyay.viewmodel.AuthViewModel
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController:NavController){

    val viewModel:AuthViewModel=viewModel()
    val state by viewModel.authState.collectAsState()

    LaunchedEffect(Unit){
        delay(1000)
        viewModel.checkCurrentUser()
    }

    LaunchedEffect(state){
        when(state){
            is AuthResult.Farmer->{
                navController.navigate("farmer_graph"){
                    popUpTo("splash"){ inclusive=true }
                }
            }
            is AuthResult.Idle->{
                navController.navigate("auth_graph"){
                    popUpTo("splash"){ inclusive=true }
                }
            }
            else->{}
        }
    }
}

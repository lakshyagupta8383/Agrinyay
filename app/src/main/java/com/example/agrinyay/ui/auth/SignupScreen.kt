package com.example.agrinyay.ui.auth

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun SignupScreen(navController:NavController){

    Scaffold { innerPadding ->

        Column(
            modifier=Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement=Arrangement.Center
        ){

            Text("Signup Screen")

            Spacer(modifier=Modifier.height(24.dp))

            Button(
                onClick={
                    navController.popBackStack()
                },
                modifier=Modifier.fillMaxWidth()
            ){
                Text("Back to Login")
            }
        }
    }
}

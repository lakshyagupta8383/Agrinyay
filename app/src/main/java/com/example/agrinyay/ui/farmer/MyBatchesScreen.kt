package com.example.agrinyay.ui.farmer
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@Composable
fun MyBatchesScreen(navController:NavController){
    Box(
        modifier=Modifier.fillMaxSize(),
        contentAlignment=Alignment.Center
    ){
        Text("My Batches")
    }
}


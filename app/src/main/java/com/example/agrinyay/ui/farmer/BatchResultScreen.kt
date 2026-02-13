package com.example.agrinyay.ui.farmer

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun BatchResultScreen(batchId:String){

    Column(
        modifier=Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement=Arrangement.Center,
        horizontalAlignment=Alignment.CenterHorizontally
    ){

        Text(
            text="Batch Created Successfully",
            style=MaterialTheme.typography.titleLarge
        )

        Spacer(modifier=Modifier.height(16.dp))

        Text("Batch ID:")
        Text(batchId)
    }
}

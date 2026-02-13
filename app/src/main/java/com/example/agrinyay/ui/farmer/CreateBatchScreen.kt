package com.example.agrinyay.ui.farmer

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.agrinyay.viewmodel.BatchState
import com.example.agrinyay.viewmodel.BatchViewModel
import com.google.firebase.auth.FirebaseAuth

@Composable
fun CreateBatchScreen(
    navController:NavController,
    crateId:String
){

    val viewModel:BatchViewModel = viewModel()
    val state by viewModel.state.collectAsState()

    var cropType by remember{ mutableStateOf("") }
    var quantity by remember{ mutableStateOf("") }

    val farmerId=FirebaseAuth.getInstance().currentUser?.uid ?: ""

    Scaffold { innerPadding ->

        Column(
            modifier=Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ){

            Text(
                text="Create Batch",
                style=MaterialTheme.typography.titleLarge
            )

            Spacer(modifier=Modifier.height(16.dp))

            Text("Crate ID: $crateId")

            Spacer(modifier=Modifier.height(24.dp))

            OutlinedTextField(
                value=cropType,
                onValueChange={cropType=it},
                label={ Text("Crop Type") },
                modifier=Modifier.fillMaxWidth()
            )

            Spacer(modifier=Modifier.height(16.dp))

            OutlinedTextField(
                value=quantity,
                onValueChange={quantity=it},
                label={ Text("Quantity (kg)") },
                modifier=Modifier.fillMaxWidth()
            )

            Spacer(modifier=Modifier.height(24.dp))

            Button(
                onClick={
                    if(cropType.isNotBlank() && quantity.isNotBlank()){
                        viewModel.createBatch(
                            farmerId,
                            crateId,
                            cropType,
                            quantity
                        )
                    }
                },
                modifier=Modifier.fillMaxWidth(),
                enabled=state !is BatchState.Loading
            ){
                if(state is BatchState.Loading){
                    CircularProgressIndicator(
                        modifier=Modifier.size(20.dp),
                        strokeWidth=2.dp
                    )
                }else{
                    Text("Create Batch")
                }
            }

            Spacer(modifier=Modifier.height(16.dp))

            if(state is BatchState.Error){
                Text(
                    text=(state as BatchState.Error).message,
                    color=MaterialTheme.colorScheme.error
                )
            }
        }
    }

    LaunchedEffect(state){
        if(state is BatchState.Success){
            val batchId=(state as BatchState.Success).batchId
            navController.navigate("batch_result/$batchId"){
                popUpTo("create_batch/$crateId"){ inclusive=true }
            }
        }
    }
}

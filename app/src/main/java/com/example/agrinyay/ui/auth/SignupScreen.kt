package com.example.agrinyay.ui.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.agrinyay.viewmodel.AuthResult
import com.example.agrinyay.viewmodel.AuthViewModel

@Composable
fun SignupScreen(navController:NavController){

    val viewModel:AuthViewModel=viewModel()
    val state by viewModel.authState.collectAsState()
    val selectedRole by viewModel.selectedRole.collectAsState()

    var email by remember{mutableStateOf("")}
    var password by remember{mutableStateOf("")}

    val gradient=Brush.verticalGradient(
        colors=listOf(
            Color(0xFFE8F5E9),
            Color(0xFFF1F8E9),
            Color.White
        )
    )

    Scaffold{padding->

        Box(
            modifier=Modifier
                .fillMaxSize()
                .background(gradient)
                .padding(padding)
        ){

            Column(
                modifier=Modifier
                    .fillMaxWidth()
                    .padding(horizontal=24.dp)
                    .align(Alignment.Center)
            ){

                Text(
                    text="Create Account 🌱",
                    style=MaterialTheme.typography.headlineLarge,
                    color=MaterialTheme.colorScheme.primary
                )

                Spacer(Modifier.height(40.dp))

                Card(
                    shape=RoundedCornerShape(28.dp),
                    elevation=CardDefaults.cardElevation(12.dp),
                    colors=CardDefaults.cardColors(containerColor=Color.White)
                ){

                    Column(
                        modifier=Modifier
                            .fillMaxWidth()
                            .padding(24.dp)
                    ){

                        // ROLE TOGGLE
                        Row(
                            modifier=Modifier.fillMaxWidth(),
                            horizontalArrangement=Arrangement.SpaceEvenly
                        ){

                            Button(
                                onClick={viewModel.setRole("farmer")},
                                colors=ButtonDefaults.buttonColors(
                                    containerColor=
                                        if(selectedRole=="farmer")
                                            MaterialTheme.colorScheme.primary
                                        else
                                            MaterialTheme.colorScheme.surfaceVariant
                                )
                            ){
                                Text("Farmer")
                            }

                            Button(
                                onClick={viewModel.setRole("customer")},
                                colors=ButtonDefaults.buttonColors(
                                    containerColor=
                                        if(selectedRole=="customer")
                                            MaterialTheme.colorScheme.primary
                                        else
                                            MaterialTheme.colorScheme.surfaceVariant
                                )
                            ){
                                Text("Customer")
                            }
                        }

                        Spacer(Modifier.height(24.dp))

                        OutlinedTextField(
                            value=email,
                            onValueChange={email=it},
                            label={Text("Email")},
                            modifier=Modifier.fillMaxWidth(),
                            singleLine=true,
                            shape=RoundedCornerShape(16.dp)
                        )

                        Spacer(Modifier.height(20.dp))

                        OutlinedTextField(
                            value=password,
                            onValueChange={password=it},
                            label={Text("Password")},
                            modifier=Modifier.fillMaxWidth(),
                            singleLine=true,
                            visualTransformation=PasswordVisualTransformation(),
                            shape=RoundedCornerShape(16.dp)
                        )

                        Spacer(Modifier.height(32.dp))

                        Button(
                            onClick={
                                viewModel.signup(email,password)
                            },
                            modifier=Modifier
                                .fillMaxWidth()
                                .height(54.dp),
                            shape=RoundedCornerShape(20.dp)
                        ){
                            Text("Sign Up")
                        }

                        Spacer(Modifier.height(16.dp))

                        TextButton(
                            onClick={
                                navController.navigate("login"){
                                    popUpTo("signup"){inclusive=true}
                                }
                            }
                        ){
                            Text("Already have an account? Login")
                        }

                        if(state is AuthResult.Error){
                            Spacer(Modifier.height(12.dp))
                            Text(
                                text=(state as AuthResult.Error).message,
                                color=MaterialTheme.colorScheme.error
                            )
                        }
                    }
                }
            }
        }
    }

    LaunchedEffect(state){
        when(state){

            is AuthResult.Farmer->{
                val uid=(state as AuthResult.Farmer).uid
                navController.navigate("my_batches/$uid"){
                    popUpTo("auth_graph"){inclusive=true}
                }
            }

            is AuthResult.Customer->{
                // Customer flow later
            }

            else->{}
        }
    }
}

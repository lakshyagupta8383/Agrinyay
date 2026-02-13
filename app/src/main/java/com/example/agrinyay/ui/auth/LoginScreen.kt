package com.example.agrinyay.ui.auth

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.agrinyay.viewmodel.AuthResult
import com.example.agrinyay.viewmodel.AuthViewModel

@Composable
fun LoginScreen(navController: NavController) {

    val viewModel: AuthViewModel = viewModel()
    val state by viewModel.authState.collectAsState()

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Scaffold { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center
        ) {

            TextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(12.dp))

            TextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(24.dp))

            Button(
                onClick = {
                    viewModel.login(email, password)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Login")
            }

            if (state is AuthResult.Error) {
                Spacer(Modifier.height(12.dp))
                Text(
                    text = (state as AuthResult.Error).message,
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    }

    LaunchedEffect(state) {
        if (state is AuthResult.Farmer) {

            val farmerId = (state as AuthResult.Farmer).uid

            navController.navigate("my_batches/$farmerId") {
                popUpTo("auth_graph") { inclusive = true }
            }
        }
    }
}

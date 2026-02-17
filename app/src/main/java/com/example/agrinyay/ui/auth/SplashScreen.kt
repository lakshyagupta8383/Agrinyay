package com.example.agrinyay.ui.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.agrinyay.utils.SessionManager
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController) {

    val context = LocalContext.current
    val gradient = Brush.verticalGradient(
        colors = listOf(Color(0xFFE8F5E9), Color(0xFFF1F8E9), Color.White)
    )

    LaunchedEffect(Unit) {
        delay(1500) // Branding delay

        // 1. Check Firebase
        val currentUser = FirebaseAuth.getInstance().currentUser
        // 2. Check Local Session
        val sessionManager = SessionManager(context)

        if (currentUser != null && sessionManager.isLoggedIn()) {

            // 3. Decide based on Role
            val role = sessionManager.getUserRole()

            if (role == "customer") {
                navController.navigate("customer_home/${currentUser.uid}") {
                    popUpTo("splash") { inclusive = true }
                }
            } else {
                // Default to Farmer
                navController.navigate("farmer_home/${currentUser.uid}") {
                    popUpTo("splash") { inclusive = true }
                }
            }
        } else {
            // Not logged in
            navController.navigate("login") {
                popUpTo("splash") { inclusive = true }
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize().background(gradient),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("AgriNyay 🌾", style = MaterialTheme.typography.headlineLarge, color = MaterialTheme.colorScheme.primary)
            Spacer(Modifier.height(8.dp))
            Text("Smart Quality. Fair Price.", color = Color(0xFF4E6E58))
        }
    }
}
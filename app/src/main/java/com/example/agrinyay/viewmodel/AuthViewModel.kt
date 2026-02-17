package com.example.agrinyay.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.agrinyay.repository.AuthRepository
import com.example.agrinyay.utils.SessionManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class AuthResult {
    object Idle : AuthResult()
    data class Farmer(val uid: String) : AuthResult()
    data class Customer(val uid: String) : AuthResult()
    data class Error(val message: String) : AuthResult()
}

class AuthViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = AuthRepository()
    // Initialize SessionManager with Application Context
    private val sessionManager = SessionManager(application.applicationContext)

    private val _authState = MutableStateFlow<AuthResult>(AuthResult.Idle)
    val authState: StateFlow<AuthResult> = _authState

    private val _selectedRole = MutableStateFlow("farmer")
    val selectedRole: StateFlow<String> = _selectedRole

    fun setRole(role: String) {
        _selectedRole.value = role
    }

    /**
     * Checks if a user is already logged in (Firebase + Local Session).
     */
    fun checkCurrentUser() {
        val user = repository.getCurrentUser()

        if (user == null) {
            _authState.value = AuthResult.Idle
        } else {
            viewModelScope.launch {
                // 1. Try to get role from local session first (Fastest)
                var role = sessionManager.getUserRole()

                // 2. If not in session, fetch from Repository/Database (Fallback)
                if (role == "farmer") { // Assuming default is farmer, verify with repo
                    role = repository.getUserRole(user.uid)
                }

                // 3. Ensure session is synced
                sessionManager.createLoginSession(role)

                if (role == "customer") {
                    _authState.value = AuthResult.Customer(user.uid)
                } else {
                    _authState.value = AuthResult.Farmer(user.uid)
                }
            }
        }
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            val result = repository.login(email, password)

            if (result.isSuccess) {
                val uid = result.getOrNull() ?: return@launch

                // Fetch the role from the database (Repository)
                val role = repository.getUserRole(uid)

                // --- SAVE SESSION (Professional Fix) ---
                sessionManager.createLoginSession(role)

                if (role == "customer") {
                    _authState.value = AuthResult.Customer(uid)
                } else {
                    _authState.value = AuthResult.Farmer(uid)
                }

            } else {
                _authState.value = AuthResult.Error(
                    result.exceptionOrNull()?.message ?: "Login failed"
                )
            }
        }
    }

    fun signup(email: String, password: String) {
        viewModelScope.launch {
            val roleToRegister = _selectedRole.value

            val result = repository.signup(
                email,
                password,
                roleToRegister
            )

            if (result.isSuccess) {
                val uid = result.getOrNull() ?: return@launch

                // --- SAVE SESSION (Professional Fix) ---
                sessionManager.createLoginSession(roleToRegister)

                if (roleToRegister == "customer") {
                    _authState.value = AuthResult.Customer(uid)
                } else {
                    _authState.value = AuthResult.Farmer(uid)
                }

            } else {
                _authState.value = AuthResult.Error(
                    result.exceptionOrNull()?.message ?: "Signup failed"
                )
            }
        }
    }

    fun logout() {
        // Clear Firebase
        repository.logout()
        // Clear Local Session
        sessionManager.logoutUser()

        _authState.value = AuthResult.Idle
    }
}
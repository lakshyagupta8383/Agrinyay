package com.example.agrinyay.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.agrinyay.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class AuthResult {
    object Idle : AuthResult()
    object Farmer : AuthResult()
    data class Error(val message: String) : AuthResult()
}

class AuthViewModel : ViewModel() {

    private val repository = AuthRepository()

    private val _authState = MutableStateFlow<AuthResult>(AuthResult.Idle)
    val authState: StateFlow<AuthResult> = _authState
    fun checkCurrentUser() {
        val user = repository.getCurrentUser()

        if (user == null) {
            _authState.value = AuthResult.Idle
        } else {
            viewModelScope.launch {
                try {
                    val role = repository.getUserRole(user.uid)

                    if (role == "farmer") {
                        _authState.value = AuthResult.Farmer
                    } else {
                        _authState.value =
                            AuthResult.Error("Not a farmer account")
                    }

                } catch (e: Exception) {
                    _authState.value =
                        AuthResult.Error(e.message ?: "Error")
                }
            }
        }
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            try {
                val result = repository.login(email, password)

                if (result.isSuccess) {
                    val role = result.getOrNull()
                    if (role == "farmer") {
                        _authState.value = AuthResult.Farmer
                    } else {
                        _authState.value =
                            AuthResult.Error("Not a farmer account")
                    }
                } else {
                    _authState.value =
                        AuthResult.Error(
                            result.exceptionOrNull()?.message
                                ?: "Login failed"
                        )
                }

            } catch (e: Exception) {
                _authState.value =
                    AuthResult.Error(e.message ?: "Error")
            }
        }
    }

    fun logout() {
        repository.logout()
        _authState.value = AuthResult.Idle
    }
}

package com.example.agrinyay.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.agrinyay.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class AuthResult {
    object Idle : AuthResult()
    data class Farmer(val uid: String) : AuthResult()
    data class Error(val message: String) : AuthResult()
}

class AuthViewModel : ViewModel() {

    private val repository = AuthRepository()

    private val _authState =
        MutableStateFlow<AuthResult>(AuthResult.Idle)
    val authState: StateFlow<AuthResult> = _authState

    fun checkCurrentUser() {

        val user = repository.getCurrentUser()

        if (user == null) {
            _authState.value = AuthResult.Idle
        } else {
            _authState.value = AuthResult.Farmer(user.uid)
        }
    }

    fun login(email: String, password: String) {

        viewModelScope.launch {

            try {
                val result = repository.login(email, password)

                if (result.isSuccess) {

                    val user = repository.getCurrentUser()

                    if (user != null) {
                        _authState.value =
                            AuthResult.Farmer(user.uid)
                    } else {
                        _authState.value =
                            AuthResult.Error("User not found")
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

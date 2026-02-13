package com.example.agrinyay.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.agrinyay.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class AuthResult{
    object Idle:AuthResult()
    data class Farmer(val uid:String):AuthResult()
    data class Customer(val uid:String):AuthResult()
    data class Error(val message:String):AuthResult()
}

class AuthViewModel:ViewModel(){

    private val repository=AuthRepository()

    private val _authState=
        MutableStateFlow<AuthResult>(AuthResult.Idle)
    val authState:StateFlow<AuthResult> = _authState

    private val _selectedRole=
        MutableStateFlow("farmer")
    val selectedRole:StateFlow<String> = _selectedRole

    fun setRole(role:String){
        _selectedRole.value=role
    }

    fun checkCurrentUser(){

        val user=repository.getCurrentUser()

        if(user==null){
            _authState.value=AuthResult.Idle
        }else{
            viewModelScope.launch{

                val role=repository.getUserRole(user.uid)

                if(role=="customer"){
                    _authState.value=AuthResult.Customer(user.uid)
                }else{
                    _authState.value=AuthResult.Farmer(user.uid)
                }
            }
        }
    }

    fun login(email:String,password:String){

        viewModelScope.launch{

            val result=repository.login(email,password)

            if(result.isSuccess){

                val uid=result.getOrNull() ?: return@launch

                val role=repository.getUserRole(uid)

                if(role=="customer"){
                    _authState.value=AuthResult.Customer(uid)
                }else{
                    _authState.value=AuthResult.Farmer(uid)
                }

            }else{
                _authState.value=
                    AuthResult.Error(
                        result.exceptionOrNull()?.message
                            ?: "Login failed"
                    )
            }
        }
    }

    fun signup(email:String,password:String){

        viewModelScope.launch{

            val result=repository.signup(
                email,
                password,
                _selectedRole.value   // ✅ FIXED HERE
            )

            if(result.isSuccess){

                val uid=result.getOrNull() ?: return@launch

                if(_selectedRole.value=="customer"){   // ✅ FIXED HERE
                    _authState.value=AuthResult.Customer(uid)
                }else{
                    _authState.value=AuthResult.Farmer(uid)
                }

            }else{
                _authState.value=
                    AuthResult.Error(
                        result.exceptionOrNull()?.message
                            ?: "Signup failed"
                    )
            }
        }
    }

    fun logout(){
        repository.logout()
        _authState.value=AuthResult.Idle
    }
}

package com.example.myapplication.ui.auth

sealed class AuthState {
    data class Error(val message: String) : AuthState()
    data object SignIn : AuthState()
    data object Register : AuthState()
    data class Success(val haveSid: Boolean) : AuthState()
    data object Loading : AuthState()

}
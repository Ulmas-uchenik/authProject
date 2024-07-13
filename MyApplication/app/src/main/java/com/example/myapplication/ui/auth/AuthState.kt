package com.example.myapplication.ui.auth

sealed class AuthState {
    data class Error(val message: String) : AuthState()
    data object SignIn : AuthState()
    data object Register : AuthState()
    data object Success : AuthState()
    data class Loading(val isFirstStart: Boolean) : AuthState()

}
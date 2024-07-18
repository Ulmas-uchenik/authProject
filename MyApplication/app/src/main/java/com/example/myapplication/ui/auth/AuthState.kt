package com.example.myapplication.ui.auth

sealed class AuthState {
    data class Error(
        val isFirstStart: Boolean,
        val message: String,
        val firstNameError: String? = null,
        val telephoneNumberError: String? = null,
        val passwordError: String? = null,
        val conformPasswordError: String? = null,
        ) : AuthState()
    data object SignIn : AuthState()
    data object Register : AuthState()
    data object Success : AuthState()
    data class Loading(val isFirstStart: Boolean) : AuthState()

}
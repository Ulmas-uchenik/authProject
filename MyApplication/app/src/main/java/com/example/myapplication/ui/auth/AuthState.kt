package com.example.myapplication.ui.auth

import com.example.myapplication.data.models.IsAuthorise

sealed class AuthState {
    data class Error(val message: String) : AuthState()
//    data object SingIn : AuthState()
//    data object Register : AuthState()
    data class Success(val haveSid: Boolean) : AuthState()
    data object Loading : AuthState()

}
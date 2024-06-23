package com.example.myapplication.ui.home.services

sealed class ServiceState {
    data class Success(val appearanceCategory: String) : ServiceState()
    data class Error(val errorString: String) : ServiceState()
    data object Loading : ServiceState()
}
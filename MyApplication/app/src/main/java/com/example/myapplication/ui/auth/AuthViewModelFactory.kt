package com.example.myapplication.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.viewModelFactory
import javax.inject.Inject

class AuthViewModelFactory @Inject constructor(
    private val viewModel: AuthViewModel
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if(modelClass.isAssignableFrom(AuthViewModel::class.java))
             viewModel as T
        else
            throw IllegalArgumentException("Unknown class name")
    }
}
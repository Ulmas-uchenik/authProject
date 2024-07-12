package com.example.myapplication

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.myapplication.ui.appointment.AppointmentViewModel
import javax.inject.Inject

class UserViewModelFactory @Inject constructor(
    private val viewModel: UserViewModel
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
            return viewModel as T
        } else throw IllegalArgumentException("Unknown class name")
    }
}
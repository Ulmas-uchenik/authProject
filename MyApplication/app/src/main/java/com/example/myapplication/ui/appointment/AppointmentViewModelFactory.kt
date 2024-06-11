package com.example.myapplication.ui.appointment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import javax.inject.Inject

class AppointmentViewModelFactory @Inject constructor(
    private val viewModel: AppointmentViewModel
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        if(modelClass.isAssignableFrom(AppointmentViewModel::class.java)) {
            return viewModel as T
        } else throw IllegalArgumentException("Unknown class name")
    }
}
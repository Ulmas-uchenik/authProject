package com.example.myapplication.ui.home.doctor_and_services

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import javax.inject.Inject

class DoctorAndServicesViewModelFactory @Inject constructor(
    private val viewModel: DoctorAndServicesViewModel
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        if (modelClass.isAssignableFrom(DoctorAndServicesViewModel::class.java)) {
            return viewModel as T
        } else throw IllegalArgumentException("Unknown name")
    }
}
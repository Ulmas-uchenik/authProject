package com.example.medicapi.mvvm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject

class MedicViewModelFactory @Inject constructor(
    private val viewModel: MedicViewModel
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MedicViewModel::class.java)) {
            return viewModel as T
        } else throw IllegalArgumentException("Unknown name")
    }
}
package com.example.myapplication.ui.appointment

import com.example.myapplication.data.models.Categories

sealed class AppointmentState {
    data class Success(val allDoctorsList: List<Categories>) : AppointmentState()
    data object Loading : AppointmentState()
    data class Error(val e: Throwable) : AppointmentState()
}
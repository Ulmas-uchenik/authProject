package com.example.myapplication.ui.appointment

sealed class AppointmentState {
    data class Success(val allDoctorsList: List<String>) : AppointmentState()
    data object Loading : AppointmentState()
    data class Error(val e: Throwable) : AppointmentState()
}
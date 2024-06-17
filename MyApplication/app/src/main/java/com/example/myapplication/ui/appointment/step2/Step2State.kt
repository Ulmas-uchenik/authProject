package com.example.myapplication.ui.appointment.step2

import com.example.myapplication.data.DoctorServices
import com.example.myapplication.ui.appointment.AppointmentState

sealed class Step2State {
    data class Success(val allDoctorServicesList: List<DoctorServices>) : Step2State()
    data object Loading : Step2State()
    data class Error(val e: Throwable) : Step2State()
}
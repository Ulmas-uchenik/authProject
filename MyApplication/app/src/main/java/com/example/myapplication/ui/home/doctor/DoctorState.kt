package com.example.myapplication.ui.home.doctor

import com.example.myapplication.data.models.DoctorInfo

sealed class DoctorState {
    data class Success(val doctorInfo: DoctorInfo) : DoctorState()
    data class Error(val error: String) : DoctorState()
    data object Loading : DoctorState()
}
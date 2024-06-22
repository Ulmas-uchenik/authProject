package com.example.myapplication.ui.home.doctor_and_services

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.MainActivity
import com.example.myapplication.data.DoctorRepository
import com.example.myapplication.ui.appointment.AppointmentState
import com.example.myapplication.ui.appointment.step2.Step2State
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class DoctorAndServicesViewModel @Inject constructor(
    private val repository: DoctorRepository
) : ViewModel() {
    private val _state = MutableStateFlow<AppointmentState>(AppointmentState.Loading)
    val state = _state.asStateFlow()

    private val _isAdultBranch = MutableStateFlow(false)
    val isAdultBranch = _isAdultBranch.asStateFlow()

    private val _chosenDoctor = MutableStateFlow("")
    val chosenDoctor = _chosenDoctor.asStateFlow()

    fun changeBranchAndGetDoctors(isAdult: Boolean, context: Context) {
        viewModelScope.launch {
            _state.value = AppointmentState.Loading
            try {
                val allDoctorList = repository.getAllDoctors(isAdult)
                val allCategories = repository.getAllCategories(context)
                _isAdultBranch.value = isAdult
//                _state.value = AppointmentState.Success(allDoctorList)
            } catch (t: Throwable) {
                _state.value = AppointmentState.Error(t)
            }
        }
    }

    fun chooseDoctor(doctor: String) {
        _chosenDoctor.value = doctor
    }
}
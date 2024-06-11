package com.example.myapplication.ui.appointment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.DoctorRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class AppointmentViewModel @Inject constructor(
    private val repository: DoctorRepository
) : ViewModel() {
    private val _state = MutableStateFlow<AppointmentState>(AppointmentState.Loading)
    val state = _state.asStateFlow()

    private val _isAdultBranch = MutableStateFlow(true)
    val isAdultBranch = _isAdultBranch.asStateFlow()

    private val _chosenDoctor = MutableStateFlow("")
    val chosenDoctor = _chosenDoctor.asStateFlow()

    init {
        changeBranchAndGetDoctors()
    }

    fun changeBranchAndGetDoctors() {
        viewModelScope.launch {
            _state.value = AppointmentState.Loading
            try {
                val isAdultTemp = !_isAdultBranch.value
                val allDoctorList = repository.getAllDoctors(isAdultTemp)
                _isAdultBranch.value = isAdultTemp
                _state.value = AppointmentState.Success(allDoctorList)
            } catch (t: Throwable) {
                _state.value = AppointmentState.Error(t)
            }
        }
    }

    fun chooseDoctor(doctor: String){
        _chosenDoctor.value = doctor
    }

}
package com.example.myapplication.ui.home.doctor_and_services

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.DoctorRepository
import com.example.myapplication.data.models.Doctor
import com.example.myapplication.data.models.DoctorInfo
import com.example.myapplication.ui.home.doctor.DoctorState
import com.example.myapplication.ui.home.services.ServiceState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class DoctorAndServicesViewModel @Inject constructor(
    private val repository: DoctorRepository
) : ViewModel() {
    private val _state = MutableStateFlow<DoctorAndServicesState>(DoctorAndServicesState.Loading)
    val state = _state.asStateFlow()

    private val _isAdultBranch = MutableStateFlow(false)
    val isAdultBranch = _isAdultBranch.asStateFlow()

    private val _chosenCategory = MutableStateFlow<List<String>>(emptyList())
    val chosenCategory = _chosenCategory.asStateFlow()

    // Service Fragment
    private val _stateService =
        MutableStateFlow<ServiceState>(ServiceState.Success("Не получилось выполнить ваш запрос, пожалуйста перезагрузите ваше приложение"))
    val stateService = _stateService.asStateFlow()

    private val _doctorState = MutableStateFlow<DoctorState>(DoctorState.Loading)
    val doctorState = _doctorState.asStateFlow()

    fun changeBranchAndGetDoctors(isAdult: Boolean, context: Context) {
        viewModelScope.launch {
            _state.value = DoctorAndServicesState.Loading
            try {
                _isAdultBranch.value = isAdult
                val categoryList = repository.getAllCategories()
                val tempDoctorList = repository.getAllDoctors()
                val doctorList = ageGroupFilter(tempDoctorList.doctors)
                _state.value = DoctorAndServicesState.Success(categoryList.categories, doctorList)
            } catch (t: Throwable) {
                _state.value = DoctorAndServicesState.Error(t.message.toString())
            }
        }
    }

    private fun ageGroupFilter(doctorList: List<Doctor>): List<Doctor> {
        val newDoctorList = mutableListOf<Doctor>()
        doctorList.forEach {
            if (isAdultBranch.value) {
                if (it.ageGroup == "adult")
                    newDoctorList.add(it)
            } else {
                if (it.ageGroup == "child")
                    newDoctorList.add(it)
            }
        }
        return newDoctorList
    }

    fun chooseService(category: String, name: String) {
        _chosenCategory.value = listOf(category, name)
    }

    fun chooseDoctor(id: String) {
        viewModelScope.launch {
            _doctorState.value = DoctorState.Loading
            try {
                val doctorInfo = repository.getDoctorInfo(id)
                _doctorState.value = DoctorState.Success(doctorInfo)
            } catch (t: Throwable) {
                _doctorState.value = DoctorState.Error(t.message.toString())
                Log.d("yes", t.message.toString())
            }
        }
    }

    fun getAppearanceCategory(category: String) {
        viewModelScope.launch {
            _stateService.value = ServiceState.Loading
            try {
                val appearanceCategory = repository.getAppearanceCategory()
                _stateService.value = ServiceState.Success(appearanceCategory)
            } catch (t: Throwable) {
                _stateService.value = ServiceState.Error(t.message.toString())
            }
        }
    }
}
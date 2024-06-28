package com.example.myapplication.ui.home.doctor_and_services

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.DoctorRepository
import com.example.myapplication.ui.appointment.AppointmentState
import com.example.myapplication.ui.home.services.ServiceState
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

    private val _chosenCategory = MutableStateFlow<List<String>>(emptyList())
    val chosenCategory = _chosenCategory.asStateFlow()

    // Service Fragment
    private val _stateService = MutableStateFlow<ServiceState>(ServiceState.Success("Не получилось выполнить ваш запрос, пожалуйста перезагрузите ваше приложение"))
    val stateService = _stateService.asStateFlow()

    fun changeBranchAndGetDoctors(isAdult: Boolean, context: Context) {
        viewModelScope.launch {
            _state.value = AppointmentState.Loading
            try {
                _isAdultBranch.value = isAdult
                val categoryList = repository.getAllCategories()
                _state.value = AppointmentState.Success(categoryList.categories)
            } catch (t: Throwable) {
                _state.value = AppointmentState.Error(t)
            }
        }
    }

    fun chooseDoctor(category: String, name: String) {
        _chosenCategory.value = listOf(category, name)
    }

    fun getAppearanceCategory(category: String) {
        viewModelScope.launch {
            _stateService.value = ServiceState.Loading
            try {
                val appearanceCategory = repository.getAppearanceCategory()
                _stateService.value = ServiceState.Success(appearanceCategory)
            } catch (t: Throwable){
                _stateService.value = ServiceState.Error(t.message.toString())
            }
        }
    }

}
package com.example.myapplication.ui.appointment

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.DoctorRepository
import com.example.myapplication.ui.appointment.step2.Step2State
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class AppointmentViewModel @Inject constructor(
    private val repository: DoctorRepository
) : ViewModel() {
    private val _state = MutableStateFlow<AppointmentState>(AppointmentState.Loading)
    val state = _state.asStateFlow()

    private val _isAdultBranch = MutableStateFlow(false)
    val isAdultBranch = _isAdultBranch.asStateFlow()

    private val _chosenCategory = MutableStateFlow<List<String>>(emptyList())
    val chosenCategory = _chosenCategory.asStateFlow()

    // Appointment Fragment Step 2
    private val _step2State = MutableStateFlow<Step2State>(Step2State.Loading)
    val step2State = _step2State.asStateFlow()


    fun changeBranchAndGetDoctors(isAdult: Boolean, context: Context) {
        viewModelScope.launch {
            _state.value = AppointmentState.Loading
            try {
                _isAdultBranch.value = isAdult
                val categoryList = repository.getAllCategories(context)
                _state.value = AppointmentState.Success(categoryList.categories)
            } catch (t: Throwable) {
                _state.value = AppointmentState.Error(t)
            }
        }
    }

    fun chooseCategory(category: String, name: String) {
        _chosenCategory.value = listOf(category, name)
    }

    fun getServices(isAdultBranch: Boolean, category: String, context: Context) {
        viewModelScope.launch {
            _step2State.value = Step2State.Loading
            try {
                val doctorServicesList = repository.getListOfServices(context, category)
                _step2State.value = Step2State.Success(doctorServicesList.services)
            } catch (t: Throwable) {
                _step2State.value = Step2State.Error(t)
            }
        }
    }

}
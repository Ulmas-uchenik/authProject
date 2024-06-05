package com.example.medicapi.mvvm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.medicapi.api.MedicRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class MedicViewModel @Inject constructor(
    private val repository: MedicRepository
) : ViewModel() {
    private val _state = MutableStateFlow<String>("")
    val state = _state.asStateFlow()

    fun getListOfFilialsName(){
        viewModelScope.launch {
            val name = repository.getListOfFilials().data.first().name
            _state.value = name
        }
    }
}
package com.example.myapplication.ui.home.doctor_and_services

import com.example.myapplication.data.models.Categories
import com.example.myapplication.data.models.Doctor

sealed class DoctorAndServicesState(){
    data class Success(val categoryList: List<Categories>, val doctorCardList: List<Doctor>) : DoctorAndServicesState()
    data class Error(val error: String) : DoctorAndServicesState()
    data object Loading : DoctorAndServicesState()
}
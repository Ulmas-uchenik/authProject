package com.example.myapplication.data

import kotlinx.coroutines.delay
import javax.inject.Inject

class DoctorRepository @Inject constructor(){
    suspend fun getAllDoctors(isAdultBranch: Boolean): List<String>{
        return if(isAdultBranch) listOf("Терапевт","Доктор","Художник","Учительница","Космонавт")
        else listOf("Детский доктор","Хирург", "Мама","Математичка","Пожарный")
    }
}
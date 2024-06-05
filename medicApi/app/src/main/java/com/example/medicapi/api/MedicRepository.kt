package com.example.medicapi.api

import com.example.medicapi.models.DepartmentsForDoctorsList
import com.example.medicapi.models.DoctorList
import com.example.medicapi.models.FilialsList
import javax.inject.Inject

class MedicRepository @Inject constructor(
    private val retrofitInstance: RetrofitInstance
) {
    suspend fun getListOfFilials(): FilialsList{
        return retrofitInstance.getMedicApi.getListOfFilials()
    }
    suspend fun getListOfDepartmentsForDoctors(): DepartmentsForDoctorsList{
        return retrofitInstance.getMedicApi.getListOfDepartmentsForDoctors()
    }
    suspend fun getListOfDoctors(department: Int, onlineMode: Int = 0): DoctorList{
        return retrofitInstance.getMedicApi.getListOfDoctors(department, onlineMode)
    }
}
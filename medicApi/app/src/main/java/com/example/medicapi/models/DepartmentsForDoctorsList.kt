package com.example.medicapi.models

data class DepartmentsForDoctorsList(
    val success: Boolean,
    val data: List<DepartmentsData>,
)

data class DepartmentsData(
    val id: Int,
    val name: String,
    val groupId: Int,
    val groupName: String,
    val doctCount: Int,
    val viewInWeb: Int,
    val viewInInfomat: Int?,
    val onlineMode: Int,
    val mediaId: String?,
    val checkData: CheckData?
)

data class CheckData(
    val code: Int,
    val text: String,
    val label: List<String>
)


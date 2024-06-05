package com.example.medicapi.models

data class FilialsList(
    val success: Boolean,
    val data: List<FilialsData>
)

data class FilialsData(
    val id: Int,
    val name: String,
    val address: String,
    val phone: String,
    val email: String,
    val mediaId: String,
    val groupName: String,
    val viewInWeb: Int,
    val workHours: String,
    val latitude: Double,
    val longitude: Double,
    val sortRegionOrder: Int
)
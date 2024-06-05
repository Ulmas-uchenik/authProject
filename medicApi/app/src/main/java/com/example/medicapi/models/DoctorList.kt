package com.example.medicapi.models

data class DoctorList(
    val success: Boolean,
    val data : List<DoctorData>
)

data class DoctorData(
    val dcode: Int,
    val name: String,
    val departmentId: Int,
    val departmentName: String,
    val filialId: Int,
    val filialName: String,
    val nearestDate: String,
    val schchooseType: Int,
    val viewInWeb: Int,
    val onlineMode: Int,
    val rateInfo: RateInfo,
    val mediaId: String?,
    val comment: String?,
    val onlineId: String?,
    val priceInfo: PriceInfo?,
)

data class RateInfo(
    val rateValue: Double,
    val markNumber: Int
)
data class PriceInfo(
    val price: Int,
    val schid: Int,
    val schname: String
)

package com.example.myapplication.data.models

import com.google.gson.annotations.SerializedName

data class DoctorList (
    @SerializedName("Doctors") val doctors: List<Doctor>,
    @SerializedName("Status") val status: String
)
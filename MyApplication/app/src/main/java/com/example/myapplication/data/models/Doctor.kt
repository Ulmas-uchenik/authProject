package com.example.myapplication.data.models

import com.google.gson.annotations.SerializedName

data class Doctor(
    @SerializedName("Id") val id: String,
    @SerializedName("Fio") val fio: String,
    @SerializedName("AgeGroup") val ageGroup: String,
    @SerializedName("Speciality") val speciality: String,
    @SerializedName("Specialization") val specialization: String,
    @SerializedName("Diplom") val diplom: String,
    @SerializedName("Accreditation") val accreditation: String,
    @SerializedName("Ucard") val ucard: String,
    @SerializedName("Description") val description: String,
    @SerializedName("Photo") val photo: String,
    @SerializedName("Sertificats") val sertificats: List<Sertificats>,
)

data class Sertificats(
    @SerializedName("Id") val id: String,
    @SerializedName("Text") val test: String
)
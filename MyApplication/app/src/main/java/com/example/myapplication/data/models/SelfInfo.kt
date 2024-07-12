package com.example.myapplication.data.models


import com.google.gson.annotations.SerializedName

data class SelfInfo(
    @SerializedName("BirthDay") val birthDay: String,
    @SerializedName("Creation") val creation: String,
    @SerializedName("Id") val id: String,
    @SerializedName("LastName") val lastName: String,
    @SerializedName("Login") val login: String,
    @SerializedName("MiddleName") val middleName: String,
    @SerializedName("Name") val name: String,
    @SerializedName("Phone") val phone: String,
    @SerializedName("PhoneConformed") val phoneConformed: String,
    @SerializedName("Status") val status: String,
    @SerializedName("Updated") val updated: String
)
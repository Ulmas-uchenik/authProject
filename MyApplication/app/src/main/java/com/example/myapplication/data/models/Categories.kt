package com.example.myapplication.data.models

import com.google.gson.annotations.SerializedName
import com.squareup.moshi.Json

data class Categories (
    @SerializedName("Id") val id: String,
    @SerializedName("Name") val name: String,
    @SerializedName("Photo") val photo: String,
)
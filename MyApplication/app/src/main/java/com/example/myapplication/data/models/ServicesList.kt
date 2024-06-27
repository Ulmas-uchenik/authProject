package com.example.myapplication.data.models

import com.google.gson.annotations.SerializedName
import com.squareup.moshi.Json

data class ServicesList (
    @SerializedName("Services") val services: List<Services>
)

data class Services (
    @SerializedName("Id")val id: String,
    @SerializedName("Description")val description: String,
    @SerializedName("Price")val price: String
)
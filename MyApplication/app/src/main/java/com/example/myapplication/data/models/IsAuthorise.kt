package com.example.myapplication.data.models

import com.google.gson.annotations.SerializedName
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
data class IsAuthorise (
    @SerializedName("Status") val status: String,
    @SerializedName("Sid") val sid: String
)
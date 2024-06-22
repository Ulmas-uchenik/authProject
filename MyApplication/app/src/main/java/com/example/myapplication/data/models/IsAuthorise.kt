package com.example.myapplication.data.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

data class IsAuthorise (
    @Json(name="status") val status: String,
    @Json(name="sid") val sid: String
)
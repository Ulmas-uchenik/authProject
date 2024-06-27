package com.example.myapplication.data.models

import com.google.gson.annotations.SerializedName
import com.squareup.moshi.Json

data class CategoriesList (
    @SerializedName("Categories") val categories: List<Categories>,
    @SerializedName("Status") val status: String
)
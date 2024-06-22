package com.example.myapplication.data.models

data class ServicesList (
    val services: List<Services>
)

data class Services (
    val id: String,
    val description: String,
    val price: String
)
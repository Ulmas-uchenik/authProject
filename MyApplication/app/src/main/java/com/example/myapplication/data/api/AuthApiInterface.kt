package com.example.myapplication.data.api

import com.example.myapplication.data.models.IsAuthorise
import retrofit2.http.GET
import retrofit2.http.Query

interface AuthApiInterface {
    @GET(value = "?Func=Register")
    suspend fun register(
        @Query("Uid") uid: String,
        @Query("Secret") secret: String,
        @Query("Login") login: String?,
        @Query("Phone") phone: String?,
    ) : IsAuthorise
}
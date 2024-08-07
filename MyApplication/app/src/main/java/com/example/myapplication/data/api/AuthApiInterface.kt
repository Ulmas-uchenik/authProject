package com.example.myapplication.data.api

import com.example.myapplication.data.models.IsAuthorise
import com.example.myapplication.data.models.SelfInfo
import retrofit2.http.GET
import retrofit2.http.Query

interface AuthApiInterface {
    @GET(value = "?Func=Register")
    suspend fun register(
        @Query("Uid") uid: String,
        @Query("Secret") secret: String,
        @Query("Login") login: String?,
        @Query("Password") password: String?,
        @Query("MiddleName") middleName: String?,
        @Query("Phone") phone: String?,
    ) : IsAuthorise

    @GET(value = "?Func=AutorizeByUid")
    suspend fun authByUid(
        @Query("Uid") uid: String,
        @Query("Secret") secret: String,
    ) : IsAuthorise

    @GET(value = "?Func=AutorizeByPhone")
    suspend fun authByPhone(
        @Query("Phone") phone: String,
        @Query("Password") password: String,
    ) : IsAuthorise

    @GET(value="?Func=GetSelfInfo")
    suspend fun getSelfInfo(@Query("Key") key : String): SelfInfo

    @GET(value="?Func=Logout")
    suspend fun logout(@Query("Key") key: String)
}
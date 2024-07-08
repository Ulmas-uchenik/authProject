package com.example.myapplication.data.api

import com.example.myapplication.data.models.CategoriesList
import com.example.myapplication.data.models.DoctorInfo
import com.example.myapplication.data.models.DoctorList
import com.example.myapplication.data.models.IsAuthorise
import com.example.myapplication.data.models.ServicesList
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Inject

interface InfodentApiInterface {
    @GET(value = "activity")
    suspend fun getUsefulClass()

    @GET(value = "?Func=Autorize")
    suspend fun authorize(
        @Query("Phone") phone: String,
        @Query("Secret") secret: String,
        @Query("Password") password: String,
    ): IsAuthorise

    @GET(value = "?Func=Categories")
    suspend fun getAllCategories(
        @Query("Key") key: String
    ) : CategoriesList

    @GET(value = "?Func=Services")
    suspend fun getServices(
        @Query("Key") key: String,
        @Query("Category") category: String
    ) : ServicesList

    @GET(value = "?Func=Doctors")
    suspend fun getAllDoctors(@Query("Key") key: String) : DoctorList

    @GET(value ="?Func=Doctor")
    suspend fun getDoctor(
        @Query("Id") id: String,
        @Query("Key") key: String
    ) : DoctorInfo

    @GET(value = "?Func=Register")
    suspend fun register(
        @Query("Uid") uid: String,
        @Query("Secret") secret: String,
        @Query("Login") login: String?,
        @Query("Phone") phone: String?,
    ) : IsAuthorise
}


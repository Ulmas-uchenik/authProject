package com.example.medicapi.api

import com.example.medicapi.models.DepartmentsForDoctorsList
import com.example.medicapi.models.DoctorList
import com.example.medicapi.models.FilialsList
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Inject

private const val BASE_URL = "https://demo.infoclinica.ru"
class RetrofitInstance @Inject constructor()  {
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(
            OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().also {
                it.level = HttpLoggingInterceptor.Level.BODY
            }).build()
        )
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val getMedicApi: GetMedicApi = retrofit.create(
        GetMedicApi::class.java
    )
}


interface GetMedicApi {
    @GET(value = "filials/list")
    suspend fun getListOfFilials(): FilialsList
    @GET(value =  "specialists/departments")
    suspend fun getListOfDepartmentsForDoctors(): DepartmentsForDoctorsList
    @GET(value =  "specialists/doctors")
    suspend fun getListOfDoctors(
        @Query("departments") departments: Int,
        @Query("onlineMode") onlineMode: Int,
        ): DoctorList

}
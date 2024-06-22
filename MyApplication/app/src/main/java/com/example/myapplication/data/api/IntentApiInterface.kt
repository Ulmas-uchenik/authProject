package com.example.myapplication.data.api

import com.example.myapplication.data.models.CategoriesList
import com.example.myapplication.data.models.IsAuthorise
import com.example.myapplication.data.models.ServicesList
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Inject

// Версия два
private val BASE_URL = "http://irtep.ru/stoma/index.php/"

class RetrofitInstance @Inject constructor() {
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(
            OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().also {
                it.level = HttpLoggingInterceptor.Level.BODY
            }).build()
        )
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val getApiInterface: IntentApiInterface = retrofit.create(
        IntentApiInterface::class.java
    )
}


interface IntentApiInterface {
    @GET(value = "activity")
    suspend fun getUsefulClass()

    @GET(value = "?func=autorize")
    suspend fun authorize(
        @Query("phone") phone: String,
        @Query("secret") secret: String,
        @Query("password") password: String,
    ): IsAuthorise

    @GET(value = "?func=categories")
    suspend fun getAllCategories(
        @Query("key") key: String
    ) : CategoriesList

    @GET(value = "?func=services")
    suspend fun getServices(
        @Query("key") key: String,
        @Query("category") category: String
    ) : ServicesList
}


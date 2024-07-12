package com.example.myapplication.data.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject


class RetrofitInstance @Inject constructor() {
    private val retrofitInfodent = Retrofit.Builder()
        .baseUrl(UrlsObject.URL_WITH_PHP)
        .client(
            OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().also {
                it.level = HttpLoggingInterceptor.Level.BODY
            }).build()
        )
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val retrofitAuth = Retrofit.Builder()
        .baseUrl(UrlsObject.URL_AUTH)
        .client(
            OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().also {
                it.level = HttpLoggingInterceptor.Level.BODY
            }).build()
        )
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val retrofitUser = Retrofit.Builder()
        .baseUrl(UrlsObject.URL_AUTH)
        .client(
            OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().also {
                it.level = HttpLoggingInterceptor.Level.BODY
            }).build()
        )
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val infodentApiInterface: InfodentApiInterface = retrofitInfodent.create(
        InfodentApiInterface::class.java
    )

    val authApiInterface: AuthApiInterface = retrofitAuth.create(
        AuthApiInterface::class.java
    )

    val userApiInterface: UserApiInterface = retrofitUser.create(
        UserApiInterface::class.java
    )
}
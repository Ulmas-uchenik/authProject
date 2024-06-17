package com.example.myapplication.data.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import java.math.BigInteger
import java.security.MessageDigest
import javax.inject.Inject

// Версия два
private val BASE_URL = "https://stoma.irtep.ru/"

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

}


fun main(){
    println(md5Hash(" 79520229480qwerty"))
}
fun md5Hash(str: String): String {
    val md = MessageDigest.getInstance("MD5")
    val bigInt = BigInteger(1, md.digest(str.toByteArray(Charsets.UTF_8)))
    return String.format("%032x", bigInt)
}

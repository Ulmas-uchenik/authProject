package com.example.myapplication.data

import android.annotation.SuppressLint
import android.app.Service
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.example.myapplication.data.api.RetrofitInstance
import com.example.myapplication.data.bd.UserDao
import com.example.myapplication.data.models.CategoriesList
import com.example.myapplication.data.models.IsAuthorise
import com.example.myapplication.data.models.ServicesList
import kotlinx.coroutines.delay
import java.math.BigInteger
import java.security.MessageDigest
import javax.inject.Inject
import kotlin.random.Random
private const val PREFERENCE_NAME = "PreferenceDoctor"
private const val SHARED_PREFS_KEY = "shared_key"
class DoctorRepository @Inject constructor(
    private val retrofitInstance: RetrofitInstance
){
    @SuppressLint("CommitPrefEdits")
    fun putKeySid(context: Context, key: String) {
        val prefs = context.getSharedPreferences(PREFERENCE_NAME, MODE_PRIVATE)
        val editor: SharedPreferences.Editor = prefs.edit()
        editor.putString(SHARED_PREFS_KEY, key)
        editor.apply()
    }

    @SuppressLint("CommitPrefEdits")
    fun exitFromAccount(context: Context) {
        val prefs = context.getSharedPreferences(PREFERENCE_NAME, MODE_PRIVATE)
        val editor: SharedPreferences.Editor = prefs.edit()
        editor.putString(SHARED_PREFS_KEY, null)
        editor.apply()
    }
    fun getKey(context: Context): String? {
        val prefs = context.getSharedPreferences(PREFERENCE_NAME, MODE_PRIVATE)
        return prefs.getString(SHARED_PREFS_KEY, null)
    }
    suspend fun authorise(phone: String, password: String): IsAuthorise {
        val numberForSecret = phone.replaceFirst("", " ")
        println(numberForSecret)
        val numberSecret = "${numberForSecret}qwerty"
        println(numberSecret)
        val secret = md5Hash(numberSecret)
        println(secret)
        return retrofitInstance.getApiInterface.authorize(" $phone", secret, password)
    }
    suspend fun getAllCategories(context: Context): CategoriesList {
        val key = getKey(context)
        return retrofitInstance.getApiInterface.getAllCategories(key!!)
    }

    suspend fun getListOfServices(context: Context, category: String): ServicesList{
        val key = getKey(context)
        return retrofitInstance.getApiInterface.getServices(key!!, category)
    }

    suspend fun getAppearanceCategory(): String{
        return "Я могу говрить много о профессии доктора, но сдержусь парочкой не приятных слов. Эта профессия включаем в себя множество жизненно важных решений от которых завсит жизнь людей. Лишь одно не правльное движение или решение доктора может убить человека или спасти ему жизнь. Поэтоу это очень поасная работа Я могу говрить много о профессии доктора, но сдержусь парочкой не приятных слов. Эта профессия включаем в себя множество жизненно важных решений от которых завсит жизнь людей. Лишь одно не правльное движение или решение доктора может убить человека или спасти ему жизнь. Поэтоу это очень поасная работа Я могу говрить много о профессии доктора, но сдержусь парочкой не приятных слов. Эта профессия включаем в себя множество жизненно важных решений от которых завсит жизнь людей. Лишь одно не правльное движение или решение доктора может убить человека или спасти ему жизнь. Поэтоу это очень поасная работа Я могу говрить много о профессии доктора, но сдержусь парочкой не приятных слов. Эта профессия включаем в себя множество жизненно важных решений от которых завсит жизнь людей. Лишь одно не правльное движение или решение доктора может убить человека или спасти ему жизнь. Поэтоу это очень поасная работа Я могу говрить много о профессии доктора, но сдержусь парочкой не приятных слов. Эта профессия включаем в себя множество жизненно важных решений от которых завсит жизнь людей. Лишь одно не правльное движение или решение доктора может убить человека или спасти ему жизнь. Поэтоу это очень поасная работа "
    }
}

fun md5Hash(str: String): String {
    val md = MessageDigest.getInstance("MD5")
    val bigInt = BigInteger(1, md.digest(str.toByteArray(Charsets.UTF_8)))
    return String.format("%032x", bigInt)
}

suspend fun main() {
    val retrofitInstance = RetrofitInstance()
    val repository = DoctorRepository(retrofitInstance)
    println(md5Hash(" 79520229480qwerty"))

    val isAuth = repository.authorise("79520229480", "123")
    println("${isAuth.status}, ${isAuth.sid}")
//
//    val allCategories = repository.getAllCategories(isAuth.sid)
//    allCategories.categories.forEach {
//        println("${it.name}, ${it.id    }")
//    }
}
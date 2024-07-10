package com.example.myapplication.data

import android.annotation.SuppressLint
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.example.myapplication.data.api.RetrofitInstance
import com.example.myapplication.data.models.CategoriesList
import com.example.myapplication.data.models.DoctorInfo
import com.example.myapplication.data.models.DoctorList
import com.example.myapplication.data.models.IsAuthorise
import com.example.myapplication.data.models.ServicesList
import dagger.hilt.android.qualifiers.ActivityContext
import java.math.BigInteger
import java.security.MessageDigest
import javax.inject.Inject

class DoctorRepository @Inject constructor(
    @ActivityContext private val context: Context,
    private val retrofitInstance: RetrofitInstance
) {
    @SuppressLint("CommitPrefEdits")
    fun exitFromAccount() {
        val prefs = context.getSharedPreferences(Const.PREFERENCE_NAME, MODE_PRIVATE)
        val editor: SharedPreferences.Editor = prefs.edit()
        editor.putString(Const.SHARED_PREFS_KEY, null)
        editor.apply()
    }

    fun getKey(): String? {
        val prefs = context.getSharedPreferences(Const.PREFERENCE_NAME, MODE_PRIVATE)
        return prefs.getString(Const.SHARED_PREFS_KEY, null)
    }

    suspend fun getAllCategories(): CategoriesList {
        val key = getKey()
        return retrofitInstance.infodentApiInterface.getAllCategories(key!!)
    }

    suspend fun getListOfService(category: String): ServicesList {
        val key = getKey()
        return retrofitInstance.infodentApiInterface.getServices(key!!, category)
    }

    suspend fun getAppearanceCategory(): String {
        return "Я могу говрить много о профессии доктора, но сдержусь парочкой не приятных слов. Эта профессия включаем в себя множество жизненно важных решений от которых завсит жизнь людей. Лишь одно не правльное движение или решение доктора может убить человека или спасти ему жизнь. Поэтоу это очень поасная работа Я могу говрить много о профессии доктора, но сдержусь парочкой не приятных слов. Эта профессия включаем в себя множество жизненно важных решений от которых завсит жизнь людей. Лишь одно не правльное движение или решение доктора может убить человека или спасти ему жизнь. Поэтоу это очень поасная работа Я могу говрить много о профессии доктора, но сдержусь парочкой не приятных слов. Эта профессия включаем в себя множество жизненно важных решений от которых завсит жизнь людей. Лишь одно не правльное движение или решение доктора может убить человека или спасти ему жизнь. Поэтоу это очень поасная работа Я могу говрить много о профессии доктора, но сдержусь парочкой не приятных слов. Эта профессия включаем в себя множество жизненно важных решений от которых завсит жизнь людей. Лишь одно не правльное движение или решение доктора может убить человека или спасти ему жизнь. Поэтоу это очень поасная работа Я могу говрить много о профессии доктора, но сдержусь парочкой не приятных слов. Эта профессия включаем в себя множество жизненно важных решений от которых завсит жизнь людей. Лишь одно не правльное движение или решение доктора может убить человека или спасти ему жизнь. Поэтоу это очень поасная работа "
    }

    suspend fun getAllDoctors(): DoctorList {
        val key = getKey()
        return retrofitInstance.infodentApiInterface.getAllDoctors(key!!)
    }

    suspend fun getDoctorInfo(id: String): DoctorInfo {
        val key = getKey()
        return retrofitInstance.infodentApiInterface.getDoctor(id = id, key = key!!)
    }
}
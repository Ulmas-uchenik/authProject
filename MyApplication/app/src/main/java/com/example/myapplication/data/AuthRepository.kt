package com.example.myapplication.data

import android.content.Context
import android.content.SharedPreferences
import com.example.myapplication.data.api.RetrofitInstance
import com.example.myapplication.data.models.IsAuthorise
import dagger.hilt.android.qualifiers.ActivityContext
import java.math.BigInteger
import java.security.MessageDigest
import javax.inject.Inject
import kotlin.random.Random

class AuthRepository @Inject constructor(
    @ActivityContext private val context: Context, private val retrofitInstance: RetrofitInstance
) {
    suspend fun register(uid: String, login: String? = null, phone: String? = null): IsAuthorise {
        val secret = md5Hash(uid)
        return retrofitInstance.authApiInterface.register(
            uid = uid, secret = secret, login = login, phone = phone
        )
    }

    suspend fun signInByUid(): IsAuthorise {
        val uid = getUid()
        val secret = md5Hash(uid!!)
        return retrofitInstance.authApiInterface.authByUid(uid, secret)
    }

    suspend fun getSelfInfo() : IsAuthorise {
        val sid = getKey()!!
        return retrofitInstance.authApiInterface.getSelfInfo(sid)
    }

    suspend fun logout(){
        val sid = getKey()!!
        return retrofitInstance.authApiInterface.logout(sid)
    }

    fun getUid(): String? {
        val prefs = context.getSharedPreferences(Const.PREFERENCE_NAME, Context.MODE_PRIVATE)
        return prefs.getString(Const.SHARED_PREFS_UID, null)
    }
    fun getKey(): String? {
        val prefs = context.getSharedPreferences(Const.PREFERENCE_NAME, Context.MODE_PRIVATE)
        return prefs.getString(Const.SHARED_PREFS_KEY, null)
    }

    fun putSid(sid: String) {
        val prefs = context.getSharedPreferences(Const.PREFERENCE_NAME, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = prefs.edit()
        editor.putString(Const.SHARED_PREFS_KEY, sid)
        editor.apply()
    }

    fun putUid(uid: String) {
        val prefs = context.getSharedPreferences(Const.PREFERENCE_NAME, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = prefs.edit()
        editor.putString(Const.SHARED_PREFS_UID, uid)
        editor.apply()
    }

    private fun md5Hash(str: String): String {
        val md = MessageDigest.getInstance("MD5")
        val bigInt = BigInteger(1, md.digest("${str}qwerty".toByteArray(Charsets.UTF_8)))
        return String.format("%032x", bigInt)
    }
}


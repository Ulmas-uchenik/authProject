package com.example.myapplication.data

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.example.myapplication.data.api.RetrofitInstance
import com.example.myapplication.data.models.IsAuthorise
import com.example.myapplication.data.models.SelfInfo
import com.example.myapplication.ui.auth.AuthViewModel
import dagger.hilt.android.qualifiers.ActivityContext
import java.math.BigInteger
import java.security.MessageDigest
import javax.inject.Inject

class AuthRepository @Inject constructor(
    @ActivityContext private val context: Context, private val retrofitInstance: RetrofitInstance
) {
    suspend fun register(uid: String, login: String? = null, middleName: String? = null, phone: String? = null, password: String? = null): IsAuthorise {
        val secret = md5Hash(uid)
        var passwordHash: String? = null
        if(password != null) passwordHash = md5Hash(password)
        Log.d("yes", "Пароль - ${password}, hash password - ${passwordHash}")
        Log.d(AuthViewModel.AUTH_TAG, "UID in app memory - ${getUid()}")
        return retrofitInstance.authApiInterface.register(
            uid = uid, secret = secret, login = login, phone = phone, middleName = middleName, password = passwordHash
        )
    }

    suspend fun signInByUid(): IsAuthorise {
        val uid = getUid()
        val secret = md5Hash(uid!!)
        return retrofitInstance.authApiInterface.authByUid(uid, secret)
    }

    suspend fun getSelfInfo() : SelfInfo {
        val sid = getKey()!!
        return retrofitInstance.authApiInterface.getSelfInfo(sid)
    }

    suspend fun logout(){
        val sid = getKey()!!
        putSid(null)
        putUid(null)
        return retrofitInstance.authApiInterface.logout(sid)
    }

    suspend fun authoriseByPhone(phone: String, password: String): IsAuthorise{
        val passwordHash = md5Hash(password)
        return retrofitInstance.authApiInterface.authByPhone(phone = phone, password = passwordHash)
    }

    fun getUid(): String? {
        val prefs = context.getSharedPreferences(Const.PREFERENCE_NAME, Context.MODE_PRIVATE)
        return prefs.getString(Const.SHARED_PREFS_UID, null)
    }
    fun getKey(): String? {
        val prefs = context.getSharedPreferences(Const.PREFERENCE_NAME, Context.MODE_PRIVATE)
        return prefs.getString(Const.SHARED_PREFS_KEY, null)
    }

    fun putSid(sid: String?) {
        val prefs = context.getSharedPreferences(Const.PREFERENCE_NAME, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = prefs.edit()
        editor.putString(Const.SHARED_PREFS_KEY, sid)
        editor.apply()
    }

    fun putUserId(userId: String) {
        val prefs = context.getSharedPreferences(Const.PREFERENCE_NAME, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = prefs.edit()
        editor.putString(Const.SHARED_PREFS_USER_ID, userId)
        editor.apply()
    }

    fun putUid(uid: String?) {
        val prefs = context.getSharedPreferences(Const.PREFERENCE_NAME, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = prefs.edit()
        editor.putString(Const.SHARED_PREFS_UID, uid)
        editor.apply()
    }

    fun tempForAdminPutUid(uid: String){
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


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
    @ActivityContext private val context: Context,
    private val retrofitInstance: RetrofitInstance
) {
    suspend fun register(login: String?, phone: String?): IsAuthorise {
        val uid = Random.nextInt(100000000, 1000000000).toString()
        val secret = md5Hash("${uid}qwerty")
        return retrofitInstance.authApiInterfacea.register(
            uid = uid,
            secret = secret,
            login = login,
            phone = phone
        )
    }

     fun putSid(sid: String){
        val prefs = context.getSharedPreferences(Const.PREFERENCE_NAME, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = prefs.edit()
        editor.putString(Const.SHARED_PREFS_KEY, sid)
        editor.apply()
    }

    private fun md5Hash(str: String): String {
        val md = MessageDigest.getInstance("MD5")
        val bigInt = BigInteger(1, md.digest(str.toByteArray(Charsets.UTF_8)))
        return String.format("%032x", bigInt)
    }
}


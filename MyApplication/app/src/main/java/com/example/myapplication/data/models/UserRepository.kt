package com.example.myapplication.data.models

import android.content.Context
import android.content.SharedPreferences
import com.example.myapplication.data.Const
import com.example.myapplication.data.api.RetrofitInstance
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject

class UserRepository @Inject constructor(
    @ActivityContext private val context: Context,
    private val retrofitInstance: RetrofitInstance
) {

    suspend fun getNotifications(): NotificationsList {
        val key = getKey()!!
        val userId = getUserId()!!
        return retrofitInstance.userApiInterface.getAllNotification(key, userId)
    }
    suspend fun getSelfInfo(): SelfInfo {
        val key = getKey()!!
        return retrofitInstance.userApiInterface.getSelfInfo(key)
    }

    suspend fun pointAllNotificationAdRead(): NotificationsList {
        val key = getKey()!!
        return retrofitInstance.userApiInterface.pointAllNotificationAdRead(key)
    }

    fun getUserId(): String? {
        val prefs = context.getSharedPreferences(Const.PREFERENCE_NAME, Context.MODE_PRIVATE)
        return prefs.getString(Const.SHARED_PREFS_USER_ID, null)
    }
    fun putUserId(userId: String) {
        val prefs = context.getSharedPreferences(Const.PREFERENCE_NAME, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = prefs.edit()
        editor.putString(Const.SHARED_PREFS_USER_ID, userId)
        editor.apply()
    }
    fun getKey(): String? {
        val prefs = context.getSharedPreferences(Const.PREFERENCE_NAME, Context.MODE_PRIVATE)
        return prefs.getString(Const.SHARED_PREFS_KEY, null)
    }
}
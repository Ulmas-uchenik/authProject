package com.example.myapplication.data.api

import com.example.myapplication.data.models.NotificationsList
import com.example.myapplication.data.models.SelfInfo
import retrofit2.http.GET
import retrofit2.http.Query

interface UserApiInterface {
    @GET(value="?Func=GetSelfInfo")
    suspend fun getSelfInfo(@Query("Key") key : String): SelfInfo

    @GET(value="?Func=NotificationsSelf")
    suspend fun getAllNotification(
        @Query("Key") key : String,
        @Query("ClientId") clientId : String,
    ): NotificationsList

    @GET(value="?Func=SetNotificationsAsRead")
    suspend fun pointAllNotificationAdRead(
        @Query("Key") key : String,
    ): NotificationsList

//    @GET(value="?Func=UpdatePassword")
//    suspend fun updatePassword(
//        @Query("Password") password : String,
//        @Query("Key") key : String,
//    ) :


}
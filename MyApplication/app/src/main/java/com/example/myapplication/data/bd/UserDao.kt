package com.example.myapplication.data.bd

import androidx.room.Dao
import androidx.room.Query
import com.example.myapplication.data.bd.models.User

@Dao
interface UserDao {
    @Query("Select * from user")
    suspend fun getAll(): User
}
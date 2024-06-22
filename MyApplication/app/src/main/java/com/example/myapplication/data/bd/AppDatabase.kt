package com.example.myapplication.data.bd

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.myapplication.data.bd.models.User

@Database(entities = [
    User::class
], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}
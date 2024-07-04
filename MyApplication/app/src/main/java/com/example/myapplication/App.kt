package com.example.myapplication

import android.app.Application
import androidx.room.Room
import com.example.myapplication.data.bd.AppDatabase
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {
    lateinit var db: AppDatabase

    override fun onCreate() {
        super.onCreate()

        db = Room.inMemoryDatabaseBuilder(
            this,
            AppDatabase::class.java
        )
            .fallbackToDestructiveMigration()
            .build()
    }

}
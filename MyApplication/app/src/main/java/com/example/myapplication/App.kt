package com.example.myapplication

import android.app.Application
import androidx.room.Room
import com.example.myapplication.data.DoctorRepository
import com.example.myapplication.data.api.RetrofitInstance
import com.example.myapplication.data.bd.AppDatabase
import com.example.myapplication.ui.auth.AUTH
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.HiltAndroidApp
import retrofit2.Retrofit

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
        AUTH = FirebaseAuth.getInstance()
    }

}
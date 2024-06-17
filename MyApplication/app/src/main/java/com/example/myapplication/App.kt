package com.example.myapplication

import android.app.Application
import com.example.myapplication.ui.auth.AUTH
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        AUTH = FirebaseAuth.getInstance()
    }
}
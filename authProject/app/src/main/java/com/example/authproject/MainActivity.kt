package com.example.authproject

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.authproject.auth.AUTH
import com.example.authproject.auth.AuthActivity
import com.example.authproject.auth.AuthViewModel
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        Log.d("yes", "on Create")
        authorizedUser()
    }

    private fun authorizedUser() {
        Log.d(AuthViewModel.AUTH_TAG, "Берем пользователя")
        AUTH = FirebaseAuth.getInstance()
        if (AUTH.currentUser != null) {
            Log.d(AuthViewModel.AUTH_TAG, "Пользователь заригистрирован")
            Toast.makeText(this, "Вы зарегистрированны", Toast.LENGTH_SHORT).show()
        } else {
            Log.d(AuthViewModel.AUTH_TAG, "Начала процесса регистрации")
            Toast.makeText(this, "Выполните регистрацию пожалуйста", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, AuthActivity::class.java))
        }
    }
}
package com.example.authproject

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private val viewMode: RegistryViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        Log.d("yes", "on Create")

    }


    override fun onStart() {
        super.onStart()
        viewMode.auth = Firebase.auth
        Log.d("yes", "on start")
        if(viewMode.auth.currentUser != null){
            Toast.makeText(this, "Вы заригистрированны", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Выполните регистрацию пожалуйста", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    override fun onRestart() {
        super.onRestart()
        Log.d("yes", "on restart")
    }
}
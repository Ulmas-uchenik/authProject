package com.example.authproject

import androidx.lifecycle.ViewModel
import com.example.authproject.auth.AUTH

class MainViewModel : ViewModel() {
    fun signOutFromAccount(){
        AUTH.signOut()
    }
}
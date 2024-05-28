package com.example.authproject

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class RegistryViewModel : ViewModel() {
    private val _phone = MutableStateFlow<String>("")
    val phone = _phone.asStateFlow()
    private val _id = MutableStateFlow<String>("")
    val id = _id.asStateFlow()
    lateinit var auth: FirebaseAuth

    fun setCode(phoneNumber: String, id: String) {
        _phone.value = phoneNumber
        _id.value = id
    }
}
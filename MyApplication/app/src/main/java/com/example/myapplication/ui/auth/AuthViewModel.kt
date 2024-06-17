package com.example.myapplication.ui.auth

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class AuthViewModel  : ViewModel() {
    private val _id = MutableStateFlow<String>("")
    val id = _id.asStateFlow()

    fun setId(id: String) {
        _id.value = id
    }

    companion object {
        const val AUTH_TAG = "auth process"
    }
}
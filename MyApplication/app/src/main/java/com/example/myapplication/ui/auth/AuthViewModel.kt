package com.example.myapplication.ui.auth

import androidx.lifecycle.ViewModel
import com.example.myapplication.data.DoctorRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class AuthViewModel @Inject constructor(
    private val repository: DoctorRepository
) : ViewModel() {
    private val _state = MutableStateFlow<AuthState>(AuthState.Success(false))
    val state = _state.asStateFlow()

    private val _userName = MutableStateFlow("")

    fun setUserName(name: String){
        _userName.value = name
    }

    fun checkUserName(name: String) = name.replace(" ", "").length >= 3

    fun setRegisterState(){
        _state.value = AuthState.Register
    }

    fun setSignInState(){
        _state.value = AuthState.SignIn
    }

    fun addPlusBeforeNumber(telephoneNumber: String) = "+7${telephoneNumber}"
    fun numberPhoneIsValid(telephoneNumber: String) =
        telephoneNumber.length == 10 || telephoneNumber.startsWith("9")


    companion object {
        const val AUTH_TAG = "auth process"
    }
}
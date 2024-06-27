package com.example.myapplication.ui.auth

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.DoctorRepository
import com.example.myapplication.data.api.RetrofitInstance
import com.example.myapplication.data.models.IsAuthorise
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AuthViewModel(
    private val repository: DoctorRepository
)  : ViewModel() {
    constructor() : this(DoctorRepository(RetrofitInstance()))
    private val _state = MutableStateFlow<AuthState>(AuthState.Success(false))
    val state = _state.asStateFlow()

    private val _id = MutableStateFlow<String>("")
    val id = _id.asStateFlow()

    private val _userWithPhonePassword = MutableStateFlow<UserWithNumberPassword>(
        UserWithNumberPassword("",""))

    fun getKey(context: Context): String? {
        return repository.getKey(context)
    }
    fun authorise(context: Context){
        viewModelScope.launch {
            _state.value = AuthState.Loading
            val isAuth: IsAuthorise
            try {
                isAuth = repository.authorise(_userWithPhonePassword.value.phone, _userWithPhonePassword.value.password)
                repository.putKeySid(context, isAuth.sid)
                _state.value = AuthState.Success(isAuth.sid.isNotEmpty())
            } catch (t: Throwable) {
                _state.value = AuthState.Error(t.message.toString())
            }
        }
    }

//    fun changeStateSign(boolean: Boolean){
//        if(boolean){
//            _state.value = AuthState.SingIn
//        } else {
//            _state.value = AuthState.Register
//        }
//    }
    fun setId(id: String) {
        _id.value = id
    }

    fun setPhonePassword(phone: String, password: String){
        _userWithPhonePassword.value = UserWithNumberPassword(
            phone = phone, password = password
        )
    }

    companion object {
        const val AUTH_TAG = "auth process"
    }
}

data class UserWithNumberPassword(
    val phone: String,
    val password: String
)
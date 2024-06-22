package com.example.myapplication.ui.auth

import android.content.Context
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

    fun getKey(context: Context): String? {
        return repository.getKey(context)
    }
    fun authorise(context: Context, phone: String, password: String){
        viewModelScope.launch {
            _state.value = AuthState.Loading
            val isAuth: IsAuthorise
            try {
                 isAuth = repository.authorise(phone, password)
                _state.value = AuthState.Success(isAuth.sid.isNotEmpty())
                repository.putKeySid(context, isAuth.sid)
            } catch (t: Throwable) {
                _state.value = AuthState.Error(t.message.toString())
            }
        }
    }
    fun setId(id: String) {
        _id.value = id
    }

    companion object {
        const val AUTH_TAG = "auth process"
    }
}
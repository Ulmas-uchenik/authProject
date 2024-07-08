package com.example.myapplication.ui.auth

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class AuthViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {
    private val _state = MutableStateFlow<AuthState>(AuthState.Loading)
    val state = _state.asStateFlow()

    private val _errorLiveData = MutableLiveData<String>()
    val errorLiveData: LiveData<String> = _errorLiveData

    private val _userName = MutableStateFlow("")

    fun setUserName(name: String) {
        _userName.value = name
    }

    fun checkUserName(name: String) = name.replace(" ", "").length >= 3

    fun setRegisterState() {
        _state.value = AuthState.Register
    }

    fun setSignInState() {
        _state.value = AuthState.SignIn
    }

    fun register(phoneNumber: String) {
        viewModelScope.launch {
            Log.d("yes", "1")
            if (numberPhoneIsValid(phoneNumber)) {
                Log.d("yes", "2")
                val phone = addSevenBeforeNumber(phoneNumber)
                Log.d("yes", "phone number - ${phone}")
                try {
                    val isAuthorise = repository.register(_userName.value, phone)
                    Log.d(
                        "yes",
                        "isAuthorize. isAuthorise.status == STATUS_OK ${isAuthorise.status == STATUS_OK}; !isAuthorise.sid.()  ${!isAuthorise.sid.isNullOrBlank()}"
                    )
                    if (isAuthorise.status == STATUS_OK && !isAuthorise.sid.isNullOrBlank()) {
                        Log.d("yes", "3")
                        repository.putSid(isAuthorise.sid)
                        _state.value = AuthState.Success(true)
                    } else {
                        Log.d("yes", "4")
                        _state.value = AuthState.Error(isAuthorise.status)
                    }
                } catch (t: Throwable){
                    Log.d("yes", "все таки с запросом какие то проблемы - ${t.message}")
                }
            } else {
                Log.d("yes", "5")
                _errorLiveData.value = "Вы ввели не верный номер телефона"
            }
        }
    }

    fun addSevenBeforeNumber(telephoneNumber: String) = "7${telephoneNumber}"
    fun numberPhoneIsValid(telephoneNumber: String) =
        telephoneNumber.length == 10 && telephoneNumber.startsWith("9")


    companion object {
        const val AUTH_TAG = "auth process"
        const val STATUS_OK = "OK"
        const val STATUS_ERROR = "Error"
    }
}
package com.example.myapplication.ui.auth

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.AuthRepository
import com.example.myapplication.ui.menu.StateMainActivity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

class AuthViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {
    private val _state = MutableStateFlow<AuthState>(AuthState.Loading)
    val state = _state.asStateFlow()

    private val _errorLiveData = MutableLiveData<String>()
    val errorLiveData: LiveData<String> = _errorLiveData

    private val _userName = MutableStateFlow(User("",""))

    private val _stateMainActivity =
        MutableStateFlow<StateMainActivity>(StateMainActivity.LoadState)
    val stateMainActivity = _stateMainActivity.asStateFlow()

    fun setUserFio(nameTemp: String, lastnameTemp: String) {
        val name = nameTemp.replace(" ", "")
        val lastname = lastnameTemp.replace(" ", "")
        _userName.value = User(name, lastname)
    }

    fun checkUserName(name: String) = name.replace(" ", "").length >= 3

    fun setRegisterState() {
        _state.value = AuthState.Register
    }

    fun setSignInState() {
        _state.value = AuthState.SignIn
    }


    fun setStateForMainActivity() {
        viewModelScope.launch {
            try {
                if (haveUid()) {
                    val isAuth = repository.getSelfInfo()
                    if (isAuth.status == STATUS_OK) {
                        _stateMainActivity.value = StateMainActivity.SignByUid
                        repository.putUserId(isAuth.id)
                    }
                    else _stateMainActivity.value = StateMainActivity.Register
                } else _stateMainActivity.value = StateMainActivity.Register
            } catch (t: Throwable) {
                Log.d(
                    AUTH_TAG,
                    "При установлении статуса захода в акканту произошла ошибка (AuthViewModel)"
                )
            }
        }
    }

    fun authByUid() {
        viewModelScope.launch {
            Log.d(AUTH_TAG, "Авторизацию по UID")
            _state.value = AuthState.Loading
            try {
                val isAuthorise = repository.signInByUid()
                if (isAuthorise.sid != null) {
                    repository.putSid(isAuthorise.sid)
                    _state.value = AuthState.Success
                }
            } catch (t: Throwable) {
                _errorLiveData.value = "Ошибпи при входе с помощью uid - ${t.message}"
                Log.d(AUTH_TAG, "Ошибка авторизации по UID - ${t.message}")
            }
        }
    }

    fun authByUidForMainActivity() {
        viewModelScope.launch {
            try {
                Log.d(AUTH_TAG, "Авторизация по UID (После входа в приложение)")
                val isAuthorise = repository.signInByUid()
                if (isAuthorise.sid != null) repository.putSid(isAuthorise.sid)
                Log.d(AUTH_TAG, "Авторизация прошла успешна sid - ${isAuthorise.sid}")
                _stateMainActivity.value = StateMainActivity.IsAuthorized
            } catch (t: Throwable) {
                Log.d(AUTH_TAG, "Ошибкаа авторизации по UID - ${t.message}")
            }
        }
    }

    fun registerSkip() {
        viewModelScope.launch {
            Log.d(AUTH_TAG, "Авторизации skip")
            _state.value = AuthState.Loading
            try {
                val uid = Random.nextInt(100000000, 1000000000).toString()
                val isAuthorise = repository.register(uid)
                if (isAuthorise.status == STATUS_OK && !isAuthorise.sid.isNullOrBlank()) {
                    Log.d(AUTH_TAG, "state auth skip - success")
                    repository.putSid(isAuthorise.sid)
                    repository.putUid(uid)
                    _state.value = AuthState.Success
                } else {
                    Log.d(AUTH_TAG, "state auth skip - error")
                    _state.value = AuthState.Error("выполнить вход с помощью skip")
                }
            } catch (t: Throwable) {
                Log.d(AUTH_TAG, "Ошибка авторизации skip - ${t.message}")
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            Log.d(AUTH_TAG, "Выход из аккантуа")
            try {
                repository.logout()
            } catch (t: Throwable) {
                Log.d(AUTH_TAG, "Ошибка выхода из аккаунта - ${t.message}")
            }
        }
    }

    fun register(phoneNumber: String) {
        viewModelScope.launch {
            Log.d(AUTH_TAG, "Регистрация")
            if (numberPhoneIsValid(phoneNumber)) {
                val phone = addSevenBeforeNumber(phoneNumber)
                try {
                    val uid = Random.nextInt(100000000, 1000000000).toString()
                    Log.d(AUTH_TAG, "UID in registration - $uid")
                    val isAuthorise = repository.register(uid, "${_userName.value.name} ${_userName.value.lastname}", phone)
                    if (isAuthorise.status == STATUS_OK && !isAuthorise.sid.isNullOrBlank()) {
                        repository.putSid(isAuthorise.sid)
                        repository.putUid(uid)
                        _state.value = AuthState.Success
                    } else {
                        Log.d(AUTH_TAG, "Пользователь уже зарегистрирован, нужен вход по uid")
                        _state.value = AuthState.Error(isAuthorise.status)
                    }
                } catch (t: Throwable) {
                    Log.d(AUTH_TAG, "При регистрации произошла ошибка - ${t.message}")
                }
            } else {
                Log.d(AUTH_TAG, "Номер телефона не валиден")
                _errorLiveData.value = "Вы ввели не верный номер телефона"
            }
        }
    }

    fun haveUid() = repository.getUid() != null
    private fun addSevenBeforeNumber(telephoneNumber: String) = "7${telephoneNumber}"
    private fun numberPhoneIsValid(telephoneNumber: String) =
        telephoneNumber.length == 10 && telephoneNumber.startsWith("9")


    companion object {
        const val AUTH_TAG = "AUTH_TAG"
        const val STATUS_OK = "OK"
        const val STATUS_ERROR = "Error"
    }

    data class User(
        val name: String,
        val lastname: String
    )
}
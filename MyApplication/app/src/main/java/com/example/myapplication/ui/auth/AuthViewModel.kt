package com.example.myapplication.ui.auth

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.myapplication.data.AuthRepository
import com.example.myapplication.data.Const
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

    private val _userName = MutableStateFlow("")

    private val _stateMainActivity =
        MutableStateFlow<StateMainActivity>(StateMainActivity.LoadState)
    val stateMainActivity = _stateMainActivity.asStateFlow()

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


    fun setStateForMainActivity() {
        viewModelScope.launch {
            try {
                if (haveUid()) {
                    val isAuth = repository.getSelfInfo()
                    if (isAuth.status == STATUS_OK) _stateMainActivity.value =
                        StateMainActivity.SignByUid
                    else _stateMainActivity.value = StateMainActivity.Register
                } else _stateMainActivity.value = StateMainActivity.Register
            } catch (t: Throwable) {
                Log.d(
                    "TAG",
                    "При установлении статуса захода в акканту произошла ошибка (AuthViewModel)"
                )
            }
        }
    }

    fun authByUid() {
        viewModelScope.launch {
            try {
                Log.d("TAG", "Авторизация по UID")
                val isAuthorise = repository.signInByUid()
                if (isAuthorise.sid != null) repository.putSid(isAuthorise.sid)
                Log.d("TAG", "Авторизация по UID, sid - ${isAuthorise.sid}")
            } catch (t: Throwable) {
                Log.d("TAG", "Попытка войти с помощь uid обернулась не удачей")
            }
        }
    }

    fun registerSkip() {
        viewModelScope.launch {
            Log.d("TAG", "Выполлняем вход с помощью skip (AuthViewModel)")
            try {
                val uid = Random.nextInt(100000000, 1000000000).toString()
                val isAuthorise = repository.register(uid, login = _userName.value)
                if (isAuthorise.status == STATUS_OK && !isAuthorise.sid.isNullOrBlank()) {
                    Log.d("TAG", "state auth skip - success")
                    repository.putSid(isAuthorise.sid)
                    repository.putUid(uid)
                    _state.value = AuthState.Success
                } else {
                    Log.d("TAG", "state auth skip - ")
                    _state.value = AuthState.Error("выполнить вход с помощью skip")
                }

            } catch (t: Throwable) {
                Log.d("TAG", "при выполлняе входа с помощью skip произошла ощибка - ${t.message} (AuthViewModel)")
            }
        }
    }

    fun logout(){
        viewModelScope.launch {
            Log.d("TAG", "Выход из аккантуа")
            try {
                repository.logout()
            } catch (t: Throwable){
                Log.d("TAG", "сделал выход из акканту и произошла ошибка - ${t.message} (AuthViewmodel)")
            }
        }
    }

    fun register(phoneNumber: String) {
        viewModelScope.launch {
            Log.d("yes", "1")
            if (numberPhoneIsValid(phoneNumber)) {
                Log.d("yes", "2")
                val phone = addSevenBeforeNumber(phoneNumber)
                Log.d("yes", "phone number - ${phone}")
                try {
                    val uid = Random.nextInt(100000000, 1000000000).toString()
                    val isAuthorise = repository.register(uid, _userName.value, phone)
                    Log.d(
                        "yes",
                        "isAuthorize. isAuthorise.status == STATUS_OK ${isAuthorise.status == STATUS_OK}; !isAuthorise.sid.()  ${!isAuthorise.sid.isNullOrBlank()}"
                    )
                    if (isAuthorise.status == STATUS_OK && !isAuthorise.sid.isNullOrBlank()) {
                        Log.d("yes", "3")
                        repository.putSid(isAuthorise.sid)
                        repository.putUid(uid)
                        _state.value = AuthState.Success
                    } else {
                        Log.d("yes", "4")
                        _state.value = AuthState.Error(isAuthorise.status)
                    }
                } catch (t: Throwable) {
                    Log.d("yes", "все таки с запросом какие то проблемы - ${t.message}")
                }
            } else {
                Log.d("yes", "5")
                _errorLiveData.value = "Вы ввели не верный номер телефона"
            }
        }
    }

    fun haveUid() = repository.getUid() != null
    private fun addSevenBeforeNumber(telephoneNumber: String) = "7${telephoneNumber}"
    private fun numberPhoneIsValid(telephoneNumber: String) =
        telephoneNumber.length == 10 && telephoneNumber.startsWith("9")


    companion object {
        const val AUTH_TAG = "auth process"
        const val STATUS_OK = "OK"
        const val STATUS_ERROR = "Error"
    }
}
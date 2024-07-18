package com.example.myapplication.ui.auth

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
    private val _state = MutableStateFlow<AuthState>(AuthState.Loading(false))
    val state = _state.asStateFlow()

    private val _errorLiveData = MutableLiveData(AuthState.Error(true, ""))
    val errorLiveData: LiveData<AuthState.Error> = _errorLiveData

    private val _userName = MutableStateFlow(User("", ""))

    private val _stateMainActivity =
        MutableStateFlow<StateMainActivity>(StateMainActivity.LoadState)
    val stateMainActivity = _stateMainActivity.asStateFlow()

    fun setUserFio(nameTemp: String, lastnameTemp: String) {
        val name = nameTemp.replace(" ", "")
        val lastname = lastnameTemp.replace(" ", "")
        _userName.value = User(name, lastname)
    }

    fun checkUserNameIsValid(name: String) = name.replace(" ", "").length >= 3

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
                    } else _stateMainActivity.value = StateMainActivity.Register
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
            _state.value = AuthState.Loading(false)
            try {
                val isAuthorise = repository.signInByUid()
                if (isAuthorise.sid != null) {
                    repository.putSid(isAuthorise.sid)
                    _state.value = AuthState.Success
                }
            } catch (t: Throwable) {
                _errorLiveData.value =
                    AuthState.Error(false, "Ошибпи при входе с помощью uid - ${t.message}")
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
            _state.value = AuthState.Loading(false)
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
                    _errorLiveData.value =
                        AuthState.Error(false, "Ошибка, не получилось пропустить")
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

    fun register(
        name: String,
        telephoneNumber: String,
        password: String,
        conformPass: String,
        lastName: String?
    ) {
        Log.d(AUTH_TAG, "Регистрация")
        val error = checkValidInfo(name, telephoneNumber, password, conformPass)
        if (error == null) {
            viewModelScope.launch {
                _state.value = AuthState.Loading(false)
                val phone = addSevenBeforeNumber(telephoneNumber)
                try {
                    val uid = Random.nextInt(100000000, 1000000000).toString()
                    Log.d(AUTH_TAG, "UID in registration - $uid")
                    val isAuthorise = repository.register(
                        uid = uid,
                        login = name,
                        middleName = lastName,
                        password = password,
                        phone = phone
                    )
                    if (isAuthorise.status == STATUS_OK && !isAuthorise.sid.isNullOrBlank()) {
                        repository.putSid(isAuthorise.sid)
                        repository.putUid(uid)
                        _state.value = AuthState.Success
                    } else {
                        Log.d(AUTH_TAG, "Пользователь уже зарегистрирован, нужен вход по uid")
                        _errorLiveData.value = AuthState.Error(false, "Пользователь с таким номером телефона уже зарегистрирован")
                    }
                } catch (t: Throwable) {
                    Log.d(AUTH_TAG, "При регистрации произошла ошибка - ${t.message}")
                    _state.value = AuthState.Error(false, "Не удалось выполнить ваш запрос регистрации")
                }
            }
        } else {
            Log.d(AUTH_TAG, "При регистрации пользователь ввел не валидные данные")
            _errorLiveData.value = error
        }
    }

    fun authoriseByPhone(telephoneNumber: String, password: String) {
        Log.d(AUTH_TAG, "Начало входа в акканту с помощью пароля")
        val error = checkPassAndPhone(telephoneNumber, password)
        if (error == null) {
            viewModelScope.launch {
                try {
                    val phone = addSevenBeforeNumber(telephoneNumber)
                    _state.value = AuthState.Loading(false)
                    val isAuth = repository.authoriseByPhone(phone, password)
                    if (isAuth.status == Const.OK) {
                        repository.putSid(isAuth.sid)
                        repository.putUid(isAuth.uid)
                        _state.value = AuthState.Success
                    } else {
                        _errorLiveData.value = AuthState.Error(false,"Логин или пароль были введены не верно")
                    }
                } catch (t: Throwable) {

                    _errorLiveData.value = AuthState.Error(false, "Не удалось выполнить ваш запрос")
                    Log.d(
                        AUTH_TAG, "При Авторизации с помощью пароля произошла ошибка - ${t.message}"
                    )
                }
            }
        } else {
            _errorLiveData.value = error
        }
    }

    private fun checkPassAndPhone(phone: String, password: String): AuthState.Error? {
        var phoneError: String? = null
        var passwordError: String? = null

        if (!numberPhoneIsValid(phone)) phoneError = "Номер телефона не валиден"
        if (password.replace(" ", "").isBlank()) passwordError = "Пароль не может быть пустым"
        else if (password.length < 8) passwordError = "Пароль должен содержать минимум 8 символов"

        return if (phoneError != null || passwordError != null) AuthState.Error(
            isFirstStart = false,
            message = "Не валидные данные",
            telephoneNumberError = phoneError,
            passwordError = passwordError
        )
        else null
    }

    private fun checkValidInfo(
        name: String, telephoneNumber: String, password: String, conformPass: String
    ): AuthState.Error? {
        var nameError: String? = null
        var passwordError: String? = null
        var conformPassError: String? = null
        var telephoneNumberError: String? = null

        if (!checkUserNameIsValid(name)) nameError = "Должно быть больше 2-х символов"
        if (password.length < 8) passwordError = "Минимум 8 символов"

        if (conformPass.isEmpty()) conformPassError = "Пароль не должет быть пустым"
        else if (conformPass != password) conformPassError = "Пароли не совпадают"


        if (!numberPhoneIsValid(telephoneNumber)) telephoneNumberError =
            "Не валидный номер телефона"

        var returnError = false
        listOf(nameError, passwordError, conformPassError, telephoneNumberError).forEach {
            if (it != null) returnError = true
        }
        return if (returnError) AuthState.Error(
            false,
            "Все данные должны быть введены верно",
            nameError,
            telephoneNumberError,
            passwordError,
            conformPassError
        ) else null
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
        val name: String, val lastname: String
    )
}
package com.example.myapplication

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.Const
import com.example.myapplication.data.models.NotificationsList
import com.example.myapplication.data.models.SelfInfo
import com.example.myapplication.data.models.UserRepository
import com.example.myapplication.ui.auth.AuthViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class UserViewModel @Inject constructor(
    private val repository: UserRepository
) : ViewModel() {
    private val _notifications = MutableStateFlow(emptyList<NotificationsList.Notification>())
    val notification = _notifications.asStateFlow()

    private val _selfInfo = MutableStateFlow<SelfInfo?>(null)
    val selfInfo = _selfInfo.asStateFlow()

    fun getNotification() {
        viewModelScope.launch {
            try {
                val userId = repository.getUserId()
                if (userId == null) {
                    val selfInfo = repository.getSelfInfo()
                    repository.putUserId(selfInfo.id)
                }
                val notificationsList = repository.getNotifications()
                if (notificationsList.status == Const.OK) {
                    _notifications.value = notificationsList.notifications.reversed()
                }
            } catch (t: Throwable) {
                Log.d(USER_TAG, "Запрос уведомлений обернулся ошибкой - ${t.message}")
            }
        }
    }

    fun putSelfInfo(){
        viewModelScope.launch {
            try {
                val selfInfo = repository.getSelfInfo()
                if(selfInfo.status == Const.OK) {
                    _selfInfo.value = selfInfo
                }
            } catch (t: Throwable) {
                Log.d(USER_TAG, "Получение информации о пользователе обернулось ошибкой - ${t.message}")
            }
        }
    }

    companion object {
        const val USER_TAG = "USER_TAG"
    }
}
package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.myapplication.ui.auth.AuthActivity
import com.example.myapplication.ui.auth.AuthViewModel
import com.example.myapplication.ui.auth.AuthViewModelFactory
import com.example.myapplication.ui.menu.StateMainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class PlugActivity : AppCompatActivity() {

    @Inject
    lateinit var authViewModelFactory: AuthViewModelFactory
    private val authViewModel: AuthViewModel by viewModels { authViewModelFactory }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_plug)

        authViewModel.setStateForMainActivity()

        lifecycleScope.launch {
            authViewModel.stateMainActivity.collect { state ->
                when (state) {
                    is StateMainActivity.IsAuthorized -> {
                        startActivity(Intent(this@PlugActivity, MainActivity::class.java))
                        this@PlugActivity.finish()
                    }

                    is StateMainActivity.Register -> {
                        startActivity(Intent(this@PlugActivity, AuthActivity::class.java))
                        this@PlugActivity.finish()
                    }

                    is StateMainActivity.SignByUid -> {
                        authViewModel.authByUidForMainActivity()
                    }

                    is StateMainActivity.LoadState -> {
                        Log.d(AuthViewModel.AUTH_TAG, "Загрузка пользователя по UID")
                    }
                }
            }
        }
    }
}
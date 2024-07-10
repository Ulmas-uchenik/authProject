package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.myapplication.data.AuthRepository
import com.example.myapplication.data.api.RetrofitInstance
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.ui.auth.AuthActivity
import com.example.myapplication.ui.auth.AuthViewModel
import com.example.myapplication.ui.auth.AuthViewModelFactory
import com.example.myapplication.ui.menu.StateMainActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    @Inject
    lateinit var viewModelFactory: AuthViewModelFactory
    private val viewModel: AuthViewModel by viewModels { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        viewModel.setStateForMainActivity()

        binding.exitFromAccount.setOnClickListener {
            viewModel.logout()
            startActivity(Intent(this, AuthActivity::class.java))
            this.finish()
        }


        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home,
                R.id.navigation_appointment,
                R.id.magazine_card,
                R.id.navigation_menu
            )
        )
        navView.setupWithNavController(navController)

        lifecycleScope.launch {
            viewModel.stateMainActivity.collect { state ->
                when(state) {
                    is StateMainActivity.Register -> {
                        startActivity(Intent(this@MainActivity, AuthActivity::class.java))
                        this@MainActivity.finish()
                    }
                    is StateMainActivity.SignByUid -> {
                        viewModel.authByUidForMainActivity()
                    }
                    is StateMainActivity.LoadState -> {
                        Log.d(AuthViewModel.AUTH_TAG , "Загрузка пользователя по UID" )
                    }
                }
            }
        }
    }
}
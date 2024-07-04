package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.myapplication.data.DoctorRepository
import com.example.myapplication.data.api.RetrofitInstance
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.ui.auth.AuthActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val repository = DoctorRepository(this, RetrofitInstance())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        isAuthorise()

        binding.exitFromAccount.setOnClickListener {
            repository.exitFromAccount()
            isAuthorise()
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

    }

    private fun isAuthorise() {
        if (repository.getKey() == null) {
            startActivity(Intent(this, AuthActivity::class.java))
            this.finish()
        }
    }
}
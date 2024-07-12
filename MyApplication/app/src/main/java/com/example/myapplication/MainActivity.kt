package com.example.myapplication

import android.Manifest.permission
import android.app.PendingIntent
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.myapplication.data.Const
import com.example.myapplication.data.models.NotificationsList
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.ui.auth.AuthViewModel
import com.example.myapplication.ui.auth.AuthViewModelFactory
import com.example.myapplication.ui.menu.notification.NotificationsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            Toast.makeText(this, "Notifications permission granted", Toast.LENGTH_SHORT)
                .show()
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                Toast.makeText(
                    this@MainActivity,
                    "Чтобы получать уведомления, выдайте разрешение",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var authViewModelFactory: AuthViewModelFactory
    private val authViewModel: AuthViewModel by viewModels { authViewModelFactory }

    @Inject
    lateinit var userViewModelFactory: UserViewModelFactory
    private val userViewModel: UserViewModel by viewModels { userViewModelFactory }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        callStartFunctions()

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


        val intent = intent
        val openFragment = intent.getStringExtra(Const.HOW_FRAGMENT_OPEN)
        Log.d("yes", "${openFragment}")
        userViewModel.putOpenFragment(openFragment?:"")

        lifecycleScope.launch {
            userViewModel.notification.collect { notificationList ->
                if (notificationList.isNotEmpty()) {
                    notificationList.forEach {
                        createNotification(it)
                    }
                }
            }
        }

        lifecycleScope.launch {
            userViewModel.getNotification()
            while(true){
                delay(30000)
                userViewModel.getNotification()
            }
        }
    }

    private fun askNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    permission.POST_NOTIFICATIONS
                ) ==
                PackageManager.PERMISSION_GRANTED
            ) {
            } else {
                requestPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }

    private fun createNotification(notificationInfo: NotificationsList.Notification) {
        if (notificationInfo.new == Const.OLD) return
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra(Const.HOW_FRAGMENT_OPEN, Const.NOTIFICATION_FRAGMENT)
        val pendingIntent =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) PendingIntent.getActivity(
                this, 0, intent, PendingIntent.FLAG_IMMUTABLE
            ) else PendingIntent.getActivity(
                this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT
            )


        val notification = NotificationCompat.Builder(this, App.NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(getString(R.string.app_name))
            .setContentText(notificationInfo.text)
            .setContentIntent(pendingIntent).setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT).build()

        if (ActivityCompat.checkSelfPermission(
                this, permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            askNotificationPermission()
            return
        }

        NotificationManagerCompat.from(this).notify(notificationInfo.id.toInt(), notification)
    }

    private fun callStartFunctions() {
        userViewModel.putSelfInfo()
    }
}
package com.example.medicapi

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.medicapi.api.MedicRepository
import com.example.medicapi.api.RetrofitInstance
import com.example.medicapi.databinding.ActivityMainBinding
import com.example.medicapi.mvvm.MedicViewModel
import com.example.medicapi.mvvm.MedicViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import ru.clinicainfo.medframework.MedFrameworkManager
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var medicViewModelFactory: MedicViewModelFactory
    private val viewModel: MedicViewModel by viewModels { medicViewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.button.setOnClickListener {
            Toast.makeText(this, "Hellow you are clicked", Toast.LENGTH_SHORT).show()
            viewModel.getListOfFilialsName()
        }

        lifecycleScope.launch {
            viewModel.state.collect {state ->
                binding.textView.text = state
            }
        }
    }
}
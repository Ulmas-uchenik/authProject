package com.example.myapplication.ui.appointment.step2

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentAppointmentSecondStepBinding
import com.example.myapplication.ui.appointment.AppointmentViewModel
import com.example.myapplication.ui.appointment.AppointmentViewModelFactory
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class AppointmentSecondStepFragment : Fragment() {

    private var _binding: FragmentAppointmentSecondStepBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: AppointmentViewModelFactory
    private val viewModel: AppointmentViewModel by activityViewModels { viewModelFactory }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAppointmentSecondStepBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getServices(viewModel.isAdultBranch.value, viewModel.chosenCategory.value[0])
        binding.doctor.text = viewModel.chosenCategory.value[1]
        val adapter = DoctorServicesListAdapter()
        binding.recyclerView.adapter = adapter

        binding.backButtonToolbar.setOnClickListener {
            findNavController().navigate(R.id.action_appointmentSecondStepFragment_to_navigation_appointment)
        }

        lifecycleScope.launch {
            viewModel.step2State.collect { state ->
                when(state) {
                    is Step2State.Loading -> {
                        binding.progressBar.isVisible = true
                        binding.recyclerView.isVisible = false
                    }
                    is Step2State.Error -> {
                        Log.d("Data", state.e.message.toString())
                        Snackbar.make(binding.recyclerView, "Произошла ошибка, не удалось сменить отделение", Snackbar.LENGTH_LONG)
                            .setBackgroundTint(Color.parseColor("#FFD85959"))
                            .show()
                        binding.progressBar.isVisible = false
                        binding.recyclerView.isVisible = true
                    }
                    is Step2State.Success -> {
                        adapter.setData(state.servicesList)
                        binding.progressBar.isVisible = false
                        binding.recyclerView.isVisible = true
                    }
                }
            }
        }
    }

}
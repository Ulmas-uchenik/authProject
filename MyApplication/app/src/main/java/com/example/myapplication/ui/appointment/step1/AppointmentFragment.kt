package com.example.myapplication.ui.appointment.step1

import android.animation.ObjectAnimator
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
import com.example.myapplication.databinding.FragmentAppointmentBinding
import com.example.myapplication.ui.appointment.AppointmentState
import com.example.myapplication.ui.appointment.AppointmentViewModel
import com.example.myapplication.ui.appointment.AppointmentViewModelFactory
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class AppointmentFragment : Fragment() {
    private var _binding: FragmentAppointmentBinding? = null
    private val binding get() = _binding!!
    @Inject
    lateinit var viewModelFactory: AppointmentViewModelFactory
    private val viewModel: AppointmentViewModel by activityViewModels { viewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAppointmentBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = DoctorListAdapter { doctor -> onDoctorClick(doctor)}
        binding.recyclerView.adapter = adapter

        binding.changeBranchButton.setOnClickListener {
            viewModel.changeBranchAndGetDoctors()
            ObjectAnimator.ofFloat(
                binding.changeBranchButton,
                View.ROTATION,
                0f,
                180f
            ).apply {
                duration = 200
            }.start()
        }

        lifecycleScope.launch {
            viewModel.isAdultBranch.collect { isAdultBranch ->
                binding.branch.text = if(isAdultBranch) "Взрослое отделение" else "Детское отделение"
            }
        }
        lifecycleScope.launch {
            viewModel.state.collect { state ->
                when (state) {
                    is AppointmentState.Loading -> {
                        binding.progressBar.isVisible = true
                        binding.recyclerView.isVisible = false
                    }
                    is AppointmentState.Error -> {
                        Log.d("Data", state.e.message.toString())
                        Snackbar.make(binding.recyclerView, "Произошла ошибка, не удалось сменить отделение", Snackbar.LENGTH_LONG)
                            .setBackgroundTint(Color.parseColor("#FFD85959"))
                            .show()
                        binding.progressBar.isVisible = false
                        binding.recyclerView.isVisible = true
                    }
                    is AppointmentState.Success -> {
                        adapter.setData(state.allDoctorsList)
                        binding.progressBar.isVisible = false
                        binding.recyclerView.isVisible = true
                    }
                }
            }
        }
    }

    private fun onDoctorClick(doctor: String){
        viewModel.chooseDoctor(doctor)
        findNavController().navigate(R.id.action_navigation_appointment_to_appointmentSecondStepFragment)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
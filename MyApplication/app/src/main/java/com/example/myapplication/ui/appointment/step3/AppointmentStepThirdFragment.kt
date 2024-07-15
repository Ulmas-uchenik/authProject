package com.example.myapplication.ui.appointment.step3

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentAppointmentStepThirdBinding
import com.example.myapplication.ui.appointment.AppointmentViewModel
import com.example.myapplication.ui.appointment.AppointmentViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
@AndroidEntryPoint
class AppointmentStepThirdFragment @Inject constructor() : Fragment() {

    @Inject
    lateinit var viewModelFactory: AppointmentViewModelFactory
    private val viewModel: AppointmentViewModel by activityViewModels { viewModelFactory }
    private var _binding: FragmentAppointmentStepThirdBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAppointmentStepThirdBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.backButtonToolbar.setOnClickListener {
            findNavController().navigate(R.id.action_appointmentStepThirdFragment_to_appointmentSecondStepFragment)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}
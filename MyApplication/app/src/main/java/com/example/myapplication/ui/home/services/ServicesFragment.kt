package com.example.myapplication.ui.home.services

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
import com.example.myapplication.databinding.FragmentServicesBinding
import com.example.myapplication.ui.appointment.AppointmentViewModel
import com.example.myapplication.ui.appointment.AppointmentViewModelFactory
import com.example.myapplication.ui.home.doctor_and_services.DoctorAndServicesEnum
import com.example.myapplication.ui.home.doctor_and_services.DoctorAndServicesViewModel
import com.example.myapplication.ui.home.doctor_and_services.DoctorAndServicesViewModelFactory
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ServicesFragment : Fragment() {
    private var _binding: FragmentServicesBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var appointmentViewModelFactory: AppointmentViewModelFactory
    private val appointmentViewModel: AppointmentViewModel by activityViewModels { appointmentViewModelFactory }

    @Inject
    lateinit var viewModelFactory: DoctorAndServicesViewModelFactory
    private val viewModel: DoctorAndServicesViewModel by activityViewModels { viewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentServicesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getAppearanceCategory(viewModel.chosenCategory.value[0])
        val adapter = ServicesListAdapter { fragment -> changeFragment(fragment)}
        binding.recyclerView.adapter = adapter
        adapter.setData(
            listOf(
                ServicesForRecyclerView(
                    title = "Врачи",
                    img = R.drawable.person_24px
                ),
                ServicesForRecyclerView(
                    title = "Услуги и цены",
                    img = R.drawable.stethoscope_24px
                )
            )
        )

        binding.backButtonToolbar.setOnClickListener {
            findNavController().navigate(R.id.action_servicesFragment_to_doctorAndServicesFragment)
        }



        lifecycleScope.launch {
            viewModel.chosenCategory.collect {
                binding.textViewCategory.text = it[1]
            }
        }
        lifecycleScope.launch {
            viewModel.stateService.collect {state ->
                when(state) {
                    is ServiceState.Error -> {
                        binding.progressBar.isVisible = false
                        Snackbar.make(binding.constraintLayout, R.string.error_when_we_try_get_appearance_category, Snackbar.LENGTH_LONG)
                            .setBackgroundTint(Color.parseColor("#FFD85959"))
                            .show()

                        binding.appearanceTextView.text = state.errorString
                    }

                    is ServiceState.Success -> {
                        binding.appearanceTextView.text = state.appearanceCategory
                        binding.progressBar.isVisible = false
                        binding.scrollView.isVisible = true
                    }
                    is ServiceState.Loading -> {
                        binding.progressBar.isVisible = true
                        binding.scrollView.isVisible = false
                    }
                }
            }
        }
    }
    private fun changeFragment(fragment: String){
        when(fragment){
            DOCTOR_FRAGMENT -> {
                viewModel.changeDoctorAndServicesEnumState(DoctorAndServicesEnum.Doctor)
                findNavController().navigate(R.id.action_servicesFragment_to_doctorAndServicesFragment)
            }
            SERVICES_FRAGMENT -> {
                Log.d("yes", "нажал на category (servi) -> category - ${viewModel.chosenCategory.value[0]}, name -> ${viewModel.chosenCategory.value[1]}")
                appointmentViewModel.chooseCategory(viewModel.chosenCategory.value[0], viewModel.chosenCategory.value[1])
                findNavController().navigate(R.id.action_servicesFragment_to_appointmentSecondStepFragment2)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        const val DOCTOR_FRAGMENT = "Врачи"
        const val SERVICES_FRAGMENT = "Услуги и цены"
    }
}
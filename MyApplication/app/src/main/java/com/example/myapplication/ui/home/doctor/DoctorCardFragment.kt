package com.example.myapplication.ui.home.doctor

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentDoctorCardBinding
import com.example.myapplication.ui.home.doctor_and_services.DoctorAndServicesViewModel
import com.example.myapplication.ui.home.doctor_and_services.DoctorAndServicesViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class DoctorCardFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: DoctorAndServicesViewModelFactory
    private val viewModel: DoctorAndServicesViewModel by activityViewModels { viewModelFactory }

    private var _binding: FragmentDoctorCardBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDoctorCardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.backButtonToolbar.setOnClickListener {
            findNavController().navigate(R.id.action_doctorCardFragment_to_doctorAndServicesFragment)
        }

        lifecycleScope.launch {
            viewModel.doctorState.collect { state ->
                when (state) {
                    is DoctorState.Loading -> {

                    }
                    is DoctorState.Success -> {
                        with(binding) {
                            val doctorInfo = state.doctorInfo
                            fioTextView.text = doctorInfo.fio
                            majorTextView.text = doctorInfo.speciality
                            diplomTextView.text = doctorInfo.diplom
                            accreditationTextView.text = doctorInfo.accreditation

                            Glide.with(doctorImageView.context)
                                .load(doctorInfo.photo)
                                .into(doctorImageView)
                        }
                    }
                    is DoctorState.Error -> {

                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
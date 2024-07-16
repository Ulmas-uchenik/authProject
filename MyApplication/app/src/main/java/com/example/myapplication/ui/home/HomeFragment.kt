package com.example.myapplication.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.UserViewModel
import com.example.myapplication.UserViewModelFactory
import com.example.myapplication.data.Const
import com.example.myapplication.databinding.FragmentHomeBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: UserViewModelFactory
    private val viewModel: UserViewModel by activityViewModels { viewModelFactory }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.appointmentCard.setOnClickListener {
            findNavController().navigate(R.id.navigation_appointment)
        }

        binding.logbookCard.setOnClickListener {
            Snackbar.make(it, "сделать переход на журнал записей", Snackbar.LENGTH_LONG)
            .show()
        }

        binding.doctorsAndServicesCard.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_home_to_doctorAndServicesFragment)
        }

        binding.profileRoom.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_home_to_profileFragment)
        }

        lifecycleScope.launch {
            viewModel.openFragment.collect {
                if(it == Const.NOTIFICATION_FRAGMENT) findNavController().navigate(R.id.action_navigation_home_to_notificationsFragment)
                viewModel.putOpenFragment("")
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
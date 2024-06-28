package com.example.myapplication.ui.appointment.step1

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
import androidx.viewpager2.widget.ViewPager2
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentAppointmentBinding
import com.example.myapplication.ui.appointment.AppointmentState
import com.example.myapplication.ui.appointment.AppointmentViewModel
import com.example.myapplication.ui.appointment.AppointmentViewModelFactory
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
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

    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager2: ViewPager2

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAppointmentBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = DoctorListAdapter { doctor, name -> onDoctorClick(doctor, name)}
        binding.recyclerView.adapter = adapter
        tabLayout = binding.tabLayout
        viewPager2 = binding.viewPager2

        tabLayout.addTab(tabLayout.newTab().setText(R.string.branch_adult))
        tabLayout.addTab(tabLayout.newTab().setText(R.string.branch_kids))

        viewModel.changeBranchAndGetDoctors(true)

        tabLayout.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab != null) {
                    viewPager2.currentItem = tab.position

                    when(tab.position){
                        0 -> {
                            viewModel.changeBranchAndGetDoctors(true)
                        }
                        1-> {
                            viewModel.changeBranchAndGetDoctors(false)
                        }
                    }
                }

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }
        })

        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                tabLayout.selectTab(tabLayout.getTabAt(position))
            }
        })

        binding.backButtonToolbar.setOnClickListener {
            findNavController().navigate(R.id.navigation_home)
        }

        lifecycleScope.launch {
            viewModel.state.collect { state ->
                when (state) {
                    is AppointmentState.Loading -> {
                        binding.progressBar.isVisible = true
                        binding.recyclerView.isVisible = false
                    }
                    is AppointmentState.Error -> {
                        Log.d("Data", state.t.message.toString())
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

    private fun onDoctorClick(category: String, name: String){
        viewModel.chooseCategory(category, name)
        findNavController().navigate(R.id.action_navigation_appointment_to_appointmentSecondStepFragment)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
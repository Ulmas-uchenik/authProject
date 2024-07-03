package com.example.myapplication.ui.home.doctor_and_services

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
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentDoctorAndServicesBinding
import com.example.myapplication.ui.appointment.step1.CategoriesListAdapter
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject
@AndroidEntryPoint
class DoctorAndServicesFragment : Fragment() {
    private var _binding: FragmentDoctorAndServicesBinding? = null
    private val binding get() = _binding!!

    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager2: ViewPager2
    private lateinit var tabLayoutDoctor: TabLayout

    @Inject
    lateinit var viewModelFactory: DoctorAndServicesViewModelFactory
    private val viewModel: DoctorAndServicesViewModel by activityViewModels { viewModelFactory }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDoctorAndServicesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val servicesAdapter = CategoriesListAdapter { service, name -> onServiceClick(service, name) }
        val doctorAdapter = DoctorAdapter { id -> onDoctorClick(id) }
        binding.recyclerView.adapter = servicesAdapter


        tabLayout = binding.tabLayout
        viewPager2 = binding.viewPager2
        tabLayoutDoctor = binding.tabLayoutDoctorServices

        tabLayout.addTab(tabLayout.newTab().setText(R.string.branch_adult))
        tabLayout.addTab(tabLayout.newTab().setText(R.string.branch_kids))

        tabLayoutDoctor.addTab(tabLayoutDoctor.newTab().setText(R.string.services))
        tabLayoutDoctor.addTab(tabLayoutDoctor.newTab().setText(R.string.doctors))

        viewModel.changeBranchAndGetDoctors(true, binding.cardDoctorServices.context)

        tabLayout.addOnTabSelectedListener(object: OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab != null) {
                    viewPager2.currentItem = tab.position
                    when(tab.position){
                        0 -> viewModel.changeBranchAndGetDoctors(true, binding.cardDoctorServices.context)
                        1-> viewModel.changeBranchAndGetDoctors(false, binding.cardDoctorServices.context)
                    }
                }

            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })

        tabLayoutDoctor.addOnTabSelectedListener(object: OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab != null) {
                    viewPager2.currentItem = tab.position
                    when(tab.position){
                        0 -> {
                            binding.recyclerView.adapter = servicesAdapter
                        }
                        1-> {
                            binding.recyclerView.adapter = doctorAdapter
                        }
                    }
                }

            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })

        viewPager2.registerOnPageChangeCallback(object : OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                tabLayout.selectTab(tabLayout.getTabAt(position))
            }
        })

        viewPager2.registerOnPageChangeCallback(object : OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                tabLayoutDoctor.selectTab(tabLayout.getTabAt(position))
            }
        })

        binding.backButtonToolbar.setOnClickListener {
            findNavController().navigate(R.id.action_doctorAndServicesFragment_to_navigation_home)
        }

        lifecycleScope.launch {
            viewModel.state.collect { state ->
                when (state) {
                    is DoctorAndServicesState.Loading -> {
                        binding.progressBar.isVisible = true
                        binding.recyclerView.isVisible = false
                    }

                    is DoctorAndServicesState.Error -> {
                        Log.d("Data", state.error)
                        Snackbar.make(
                            binding.recyclerView,
                            "Произошла ошибка, не удалось сменить отделение",
                            Snackbar.LENGTH_LONG
                        )
                            .setBackgroundTint(Color.parseColor("#FFD85959"))
                            .show()
                        binding.progressBar.isVisible = false
                        binding.recyclerView.isVisible = true
                    }

                    is DoctorAndServicesState.Success -> {
                        servicesAdapter.setData(state.categoryList)
                        doctorAdapter.setData(state.doctorCardList)
                        binding.progressBar.isVisible = false
                        binding.recyclerView.isVisible = true
                    }
                }
            }
        }
    }

    private fun onServiceClick(service: String, name: String) {
        viewModel.chooseService(service, name)
        findNavController().navigate(R.id.action_doctorAndServicesFragment_to_servicesFragment)
    }

    private fun onDoctorClick(id: String){
        viewModel.chooseDoctor(id)
        findNavController().navigate(R.id.action_doctorAndServicesFragment_to_doctorCardFragment)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
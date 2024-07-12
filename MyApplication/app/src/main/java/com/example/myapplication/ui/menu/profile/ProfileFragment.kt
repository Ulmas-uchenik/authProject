package com.example.myapplication.ui.menu.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.UserViewModel
import com.example.myapplication.UserViewModelFactory
import com.example.myapplication.databinding.FragmentProfileBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: UserViewModelFactory
    private val viewModel: UserViewModel by activityViewModels { viewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setAllText()

        binding.backButtonToolbar.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_navigation_menu)
        }

    }

    fun setAllText() {
        val selfInfo = viewModel.selfInfo.value
        if (selfInfo != null) {
            binding.fioEditText.setText("${selfInfo.login}")
            binding.editTextBirthday.setText("${selfInfo.birthDay}")
            binding.fioTextView.text = selfInfo.login
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
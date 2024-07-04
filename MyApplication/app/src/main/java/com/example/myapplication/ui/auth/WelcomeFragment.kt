package com.example.myapplication.ui.auth

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentWelcomeBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class WelcomeFragment : Fragment() {
    private var _binding: FragmentWelcomeBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: AuthViewModelFactory
    private val viewModel: AuthViewModel by activityViewModels { viewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWelcomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.continueButton.setOnClickListener {
            val name = binding.nameEditText.text.toString()
            if(viewModel.checkUserName(name)) {
                viewModel.setUserName(name)
                findNavController().navigate(R.id.action_welcomeFragment_to_enterPhoneFragment)
            } else {
                Snackbar.make(it, "Чтобы продолжить введие хотябы 3 буквы", Snackbar.LENGTH_LONG)
                    .setBackgroundTint(Color.parseColor("#FFD85959"))
                    .show()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
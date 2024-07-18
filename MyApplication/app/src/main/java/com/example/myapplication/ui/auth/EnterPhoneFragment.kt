package com.example.myapplication.ui.auth

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.myapplication.MainActivity
import com.example.myapplication.databinding.FragmentEnterPhoneBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class EnterPhoneFragment : Fragment() {
    @Inject
    lateinit var viewModelFactory: AuthViewModelFactory
    private val viewModel: AuthViewModel by activityViewModels { viewModelFactory }

    private var _binding: FragmentEnterPhoneBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEnterPhoneBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.changeOnSignInButton.setOnClickListener {
            viewModel.setSignInState()
        }

        binding.changeOnRegisterButton.setOnClickListener {
            viewModel.setRegisterState()
        }

        binding.registerButton.setOnClickListener {
            val name = binding.nameEditText.text.toString()
            val telephoneNumber = binding.editTelephoneNumber.text.toString()
            val password = binding.editPass.text.toString()
            val conformPass = binding.editPassAgain.text.toString()
            val lastname = binding.lastnameEditText.text.toString()
            viewModel.register(name = name, telephoneNumber = telephoneNumber, password = password, conformPass = conformPass, lastName = lastname)
        }

        binding.signInButton.setOnClickListener {
            val phone = binding.editTelephoneNumber.text.toString()
            val password = binding.editPass.text.toString()
            viewModel.authoriseByPhone(phone, password)
        }

        binding.skipButton.setOnClickListener {
            viewModel.registerSkip()
        }


        lifecycleScope.launch {
            viewModel.state.collect { state ->
                when (state) {
                    is AuthState.Success -> {
                        startActivity(
                            Intent(
                                binding.editPass.context, MainActivity::class.java
                            )
                        )
                        activity?.finish()
                    }

                    is AuthState.Loading -> {
                        if (!state.isFirstStart) onLoadingState(true)
                    }

                    is AuthState.Error -> {
                        onLoadingState(false)
                    }

                    is AuthState.SignIn -> {
                        onLoadingState(false)
                        isRegisterViewDisable(false)
                    }

                    is AuthState.Register -> {
                        binding.layoutEditTextName.error = null
                        onLoadingState(false)
                        isRegisterViewDisable(true)
                    }
                }
            }
        }

        viewModel.errorLiveData.observe(viewLifecycleOwner) { state ->
            onLoadingState(false)
            if (!state.isFirstStart) {
                Snackbar.make(
                    binding.editPass,
                    "Ошибка регистрации - ${state.message}",
                    Snackbar.LENGTH_LONG
                ).setBackgroundTint(Color.parseColor("#FFD85959")).show()
                binding.layoutEditTextName.error = state.firstNameError
                binding.layoutEditTextTelephoneNumber.error = state.telephoneNumberError
                binding.layoutEditPass.error = state.passwordError
                binding.layoutEditPassAgain.error = state.conformPasswordError
            }
        }
    }

    private fun isRegisterViewDisable(isRegister: Boolean) {
        val visibleRegister = if (isRegister) View.VISIBLE else View.GONE
        val visibleSignIn = if (!isRegister) View.VISIBLE else View.GONE

        with(binding) {
            layoutEditTextName.visibility = visibleRegister
            layoutLastnameEditText.visibility = visibleRegister
            layoutEditPassAgain.visibility = visibleRegister
            changeOnSignInButton.visibility = visibleRegister


            changeOnRegisterButton.visibility = visibleSignIn
            signInButton.visibility = visibleSignIn
            registerButton.visibility = visibleRegister

        }
    }

    private fun onLoadingState(isLoad: Boolean) {
        val isLoading = !isLoad
        with(binding) {
            allEditTextLayout.visibility = if(isLoading) View.VISIBLE else View.INVISIBLE
            progressBar.visibility = if (isLoading) View.GONE else View.VISIBLE

            skipButton.isEnabled = isLoading
            registerButton.isEnabled = isLoading
            signInButton.isEnabled = isLoading
        }
    }
}
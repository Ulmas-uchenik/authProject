package com.example.myapplication.ui.auth

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.myapplication.MainActivity
import com.example.myapplication.databinding.FragmentEnterPhoneBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.PhoneAuthProvider
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class EnterPhoneFragment : Fragment() {
    @Inject
    lateinit var viewModelFactory: AuthViewModelFactory
    private val viewModel: AuthViewModel by activityViewModels { viewModelFactory }

    // Инициализация callback
    private lateinit var mCallback: PhoneAuthProvider.OnVerificationStateChangedCallbacks

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
            val telephoneNumber = binding.editTelephoneNumber.text
            viewModel.register(binding.editTelephoneNumber.text.toString())
            Log.d("yes", "Telephone - is ${binding.editTelephoneNumber.text.toString()}")
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
                        AuthActivity().finish()
                    }

                    is AuthState.Loading -> {
                        Snackbar.make(binding.editPass, "Загрузка", Snackbar.LENGTH_SHORT).show()
                    }

                    is AuthState.Error -> {
                        Snackbar.make(binding.editPass , "Ошибка регистрации - ${state.message}", Snackbar.LENGTH_LONG)
                            .setBackgroundTint(Color.parseColor("#FFD85959")).show()
                    }

                    is AuthState.SignIn -> {
                        with(binding) {
                            changeOnSignInButton.visibility = View.GONE
                            changeOnRegisterButton.visibility = View.VISIBLE
                            layoutEditPass.visibility = View.VISIBLE

                            signInButton.visibility = View.VISIBLE
                            registerButton.visibility = View.GONE
                        }
                    }

                    is AuthState.Register -> {
                        with(binding) {
                            layoutEditPass.visibility = View.GONE
                            changeOnSignInButton.visibility = View.VISIBLE
                            changeOnRegisterButton.visibility = View.GONE

                            signInButton.visibility = View.GONE
                            registerButton.visibility = View.VISIBLE
                        }
                    }
                }
            }
        }
        viewModel.errorLiveData.observe(viewLifecycleOwner) { error ->
            Snackbar.make(binding.editPass , "Ошибка регистрации - ${error}", Snackbar.LENGTH_LONG)
                .setBackgroundTint(Color.parseColor("#FFD85959")).show()
        }
    }
}
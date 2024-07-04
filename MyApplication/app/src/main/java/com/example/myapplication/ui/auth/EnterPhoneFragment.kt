package com.example.myapplication.ui.auth

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.myapplication.MainActivity
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentEnterPhoneBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
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


        lifecycleScope.launch {
            viewModel.state.collect { state ->
                when (state) {
                    is AuthState.Success -> {
//                        if (state.haveSid) {
//                            startActivity(
//                                Intent(
//                                    binding.editText.context, MainActivity::class.java
//                                )
//                            )
//                            AuthActivity().finish()
//                        }
                    }

                    is AuthState.Loading -> {
                        Snackbar.make(binding.editText, "Загрузка", Snackbar.LENGTH_SHORT).show()
                    }

                    is AuthState.Error -> {
                        Snackbar.make(binding.editText, state.message, Snackbar.LENGTH_LONG)
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
    }
}
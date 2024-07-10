package com.example.myapplication.ui.auth

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.myapplication.MainActivity
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentEnterCodeBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.PhoneAuthProvider
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.log

@AndroidEntryPoint
class EnterCodeFragment : Fragment() {
    @Inject
    lateinit var viewModelFactory: AuthViewModelFactory
    private val viewModel: AuthViewModel by activityViewModels { viewModelFactory }

    private var _binding: FragmentEnterCodeBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEnterCodeBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.checkCode.setOnClickListener {

        }

//        lifecycleScope.launch {
//            viewModel.state.collect { state ->
//                when (state) {
//                    is AuthState.Success -> {
//                        Log.d(
//                            "yes",
//                            "view model collect on enter conde, you have a success, have sid = ${state.haveSid}"
//                        )
//
//                        if (state.haveSid) {
//                            startActivity(
//                                Intent(
//                                    binding.editTextCode.context, MainActivity::class.java
//                                )
//                            )
//                            Log.d("yes", "вы успешно вошли в сервис через enter code")
//                            AuthActivity().finish()
//                        }
//                    }
//
//                    is AuthState.Loading -> {
//                        Log.d("yes", "view model collect on enter conde, you have a loading")
//
//                        Snackbar.make(binding.editTextCode, "Загрузка", Snackbar.LENGTH_SHORT)
//                            .show()
//                    }
//
//                    is AuthState.SignIn -> {}
//                    is AuthState.Register -> {}
//
//                    is AuthState.Error -> {
//                        Log.d("yes", "view model collect on enter conde, you have a Error")
//                        Snackbar.make(
//                            binding.editTextCode,
//                            "Вы не правильно ввели пароль не верно",
//                            Snackbar.LENGTH_LONG
//                        )
//                            .setBackgroundTint(Color.parseColor("#FFD85959")).show()
//                        findNavController().navigate(R.id.action_enterCodeFragment_to_enterPhoneFragment)
//                    }
//                }
//            }
//        }
    }
}
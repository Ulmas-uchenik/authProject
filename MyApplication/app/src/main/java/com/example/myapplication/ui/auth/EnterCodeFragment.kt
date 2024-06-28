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
    private val viewModel: AuthViewModel by activityViewModels {viewModelFactory}

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
            checkSmsCode(binding.layoutEditText.context)
        }

        lifecycleScope.launch {
            viewModel.state.collect {state ->
                when (state) {
                    is AuthState.Success -> {
                        Log.d("yes", "view model collect on enter conde, you have a success, have sid = ${state.haveSid}")

                        if (state.haveSid) {
                            startActivity(
                                Intent(
                                    binding.editTextCode.context, MainActivity::class.java
                                )
                            )
                            Log.d("yes", "вы успешно вошли в сервис через enter code")
                            AuthActivity().finish()
                        }
                    }

                    is AuthState.Loading -> {
                        Log.d("yes", "view model collect on enter conde, you have a loading")

                        Snackbar.make(binding.editTextCode, "Загрузка", Snackbar.LENGTH_SHORT).show()
                    }

                    is AuthState.Error -> {
                        Log.d("yes", "view model collect on enter conde, you have a Error")
                        Snackbar.make(binding.editTextCode, "Вы не правильно ввели пароль не верно", Snackbar.LENGTH_LONG)
                            .setBackgroundTint(Color.parseColor("#FFD85959")).show()
                        findNavController().navigate(R.id.action_enterCodeFragment_to_enterPhoneFragment)
                    }
                }
            }
        }
    }


    private fun checkSmsCode(context: Context){
        if (binding.editTextCode.text.toString().length == 6) {
            try {
                Log.d(AuthViewModel.AUTH_TAG, "Создаем credential")
                val credential = PhoneAuthProvider.getCredential(
                    viewModel.id.value.toString(),
                    binding.editTextCode.text.toString().trim()
                )
                Log.d(AuthViewModel.AUTH_TAG, "Выполняем вход в аккаунт")
                AUTH.signInWithCredential(credential).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                         viewModel.authorise()
//                        Toast.makeText(context, "добро пожаловать ", Toast.LENGTH_SHORT).show()
//                        Log.d(AuthViewModel.AUTH_TAG, "Вход в акканут произошел успешно")
//                        startActivity(Intent(context, MainActivity::class.java))
//                        AuthActivity().finish()
                    } else {
                        Log.d(AuthViewModel.AUTH_TAG, "при выполнении входа в акканут произошла ошибка - ${task.exception?.message}")
                        Toast.makeText(
                            context,
                            "При входе в аккант произошла ошибка",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            } catch (e: Throwable) {
                Log.d(AuthViewModel.AUTH_TAG, "Произошла ошибка при входе в акканут после получения смс - ${e.message}")
            }
        } else {
            Toast.makeText(context, "Ваедие верный смс код пожалуйста", Toast.LENGTH_SHORT)
                .show()
            Log.d(AuthViewModel.AUTH_TAG, "Введен не корректный смс")
        }
    }
}
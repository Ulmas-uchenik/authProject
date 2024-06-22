package com.example.myapplication.ui.auth

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
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.myapplication.MainActivity
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentEnterPhoneBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class EnterPhoneFragment : Fragment() {
    private val viewModel: AuthViewModel by activityViewModels()

    // Инициализация callback
    private lateinit var mCallback: PhoneAuthProvider.OnVerificationStateChangedCallbacks

    private var _binding: FragmentEnterPhoneBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        createCallbackForPhoneProvider()
        _binding = FragmentEnterPhoneBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // проверка номера телефона
        binding.checkTelephoneNumber.setOnClickListener {
            val tempPhoneNumber = binding.editText.text
            if (tempPhoneNumber.isNullOrEmpty() || tempPhoneNumber.length < 9) {
                Toast.makeText(context, "Введите номер тефона верно пожалуйста", Toast.LENGTH_SHORT)
                    .show()
            } else {
                val phoneNumber = "+${tempPhoneNumber}"
                Toast.makeText(context, "ваш номер телефона :$phoneNumber", Toast.LENGTH_SHORT)
                    .show()
                Log.d(AuthViewModel.AUTH_TAG, "Создание phoneProvider")
                viewModel.authorise(binding.editText.context, tempPhoneNumber.toString(), binding.editPass.text.toString())
//                PhoneAuthProvider.getInstance().verifyPhoneNumber(
//                    phoneNumber, 10, TimeUnit.SECONDS,
//                    activity as AuthActivity,
//                    mCallback
//                )
            }

        }

        lifecycleScope.launch {
            viewModel.state.collect { state ->
                when(state) {
                    is AuthState.Success -> {
                        if(state.haveSid){
                            startActivity(Intent(binding.editText.context, MainActivity::class.java))
                            AuthActivity().finish()
                        }
                    }
                    is AuthState.Loading -> {
                        Snackbar.make(binding.editText, "Загрузка", Snackbar.LENGTH_SHORT)
                            .show()
                    }
                    is AuthState.Error -> {
                        Snackbar.make(binding.editText, state.message, Snackbar.LENGTH_LONG)
                            .setBackgroundTint(Color.parseColor("#FFD85959"))
                            .show()
                    }
                }
            }
        }
    }

    private fun createCallbackForPhoneProvider() {
        Log.d(AuthViewModel.AUTH_TAG, "Создаем callBack")
        mCallback = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                AUTH.signInWithCredential(credential).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d(AuthViewModel.AUTH_TAG, "Пользователь вошел в акк")
                        Toast.makeText(context, "добро пожаловать", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(context, MainActivity::class.java))
                    } else {
                        Log.d(
                            AuthViewModel.AUTH_TAG,
                            "после входа в акк произошла ошибка - ${task.exception?.message}"
                        )
                        Toast.makeText(
                            context,
                            "У вас случились какие-то проблемы",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }

            override fun onVerificationFailed(p0: FirebaseException) {
                Toast.makeText(context, "onVerificationsFailed ${p0.message}", Toast.LENGTH_SHORT)
                    .show()
                Log.d(AuthViewModel.AUTH_TAG, "CallBack вернул ошибку - ${p0.message}")
            }

            override fun onCodeSent(id: String, token: PhoneAuthProvider.ForceResendingToken) {
                Log.d(AuthViewModel.AUTH_TAG, "Отправка кода на талефон пользователя ")
                viewModel.setId(id)
                findNavController().navigate(R.id.action_enterPhoneFragment_to_enterCodeFragment)
            }
        }
    }
}
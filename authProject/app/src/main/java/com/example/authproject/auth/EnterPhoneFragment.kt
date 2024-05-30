package com.example.authproject.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.authproject.MainActivity
import com.example.authproject.R
import com.example.authproject.databinding.FragmentEnterPhoneBinding
import com.google.firebase.FirebaseException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit

class EnterPhoneFragment : Fragment() {
    private val viewModel: AuthViewModel by activityViewModels()

    // Инициализация callback
    private lateinit var mCallback:PhoneAuthProvider.OnVerificationStateChangedCallbacks

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
            if (tempPhoneNumber.isNullOrEmpty() || tempPhoneNumber.length != 10) {
                Toast.makeText(context, "Введите номер тефона верно пожалуйста", Toast.LENGTH_SHORT).show()
            } else {
                val phoneNumber = "+7${tempPhoneNumber}"
                Toast.makeText(context, "ваш номер телефона :$phoneNumber", Toast.LENGTH_SHORT).show()
                Log.d(AuthViewModel.AUTH_TAG, "Создание phoneProvider")
                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                    phoneNumber, 10, TimeUnit.SECONDS,
                    activity as AuthActivity,
                    mCallback
                )
            }
        }
    }
    private fun createCallbackForPhoneProvider(){
        Log.d(AuthViewModel.AUTH_TAG, "Создаем callBack")
        mCallback = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks(){
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                AUTH.signInWithCredential(credential).addOnCompleteListener { task ->
                    if(task.isSuccessful){
                        Log.d(AuthViewModel.AUTH_TAG, "Пользователь вошел в акк")
                        Toast.makeText(context, "добро пожаловать", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(context, MainActivity::class.java))
                    } else {
                        Log.d(AuthViewModel.AUTH_TAG, "после входа в акк произошла ошибка - ${task.exception?.message}")
                        Toast.makeText(
                            context,
                            "У вас случились какие-то проблемы",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }

            override fun onVerificationFailed(p0: FirebaseException) {
                Toast.makeText(context, "onVerificationsFailed ${p0.message}", Toast.LENGTH_SHORT).show()
                Log.d(AuthViewModel.AUTH_TAG, "CallBack вернул ошибку - ${p0.message}")
            }

            override fun onCodeSent(id: String, token: PhoneAuthProvider.ForceResendingToken) {
                Log.d(AuthViewModel.AUTH_TAG, "Отправка кода на талефон пользователя ")
                viewModel.setId(id)
                findNavController().navigate(R.id.action_enterPhoneFragment2_to_enterCodeFragment)
            }
        }
    }
}
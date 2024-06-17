package com.example.myapplication.ui.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.example.myapplication.MainActivity
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentEnterCodeBinding
import com.google.firebase.auth.PhoneAuthProvider

class EnterCodeFragment : Fragment() {

    private val viewModel: AuthViewModel by activityViewModels()

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
            checkSmsCode()
        }
    }


    private fun checkSmsCode(){
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
                        Toast.makeText(context, "добро пожаловать ", Toast.LENGTH_SHORT).show()
                        Log.d(AuthViewModel.AUTH_TAG, "Вход в акканут произошел успешно")
                        startActivity(Intent(context, MainActivity::class.java))
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
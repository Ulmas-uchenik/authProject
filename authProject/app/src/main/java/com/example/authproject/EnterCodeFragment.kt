package com.example.authproject

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.example.authproject.databinding.FragmentEnterCodeBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider

class EnterCodeFragment : Fragment() {
    private val viewModel: RegistryViewModel by viewModels()

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
            Log.d("yes", "строчка 37 файл введения смс кода")
            if (binding.editText.text.toString().length == 6) {
                val credential = PhoneAuthProvider.getCredential(
                    viewModel.id.value,
                    binding.editText.text.toString()
                )
                viewModel.auth.signInWithCredential(credential).addOnCompleteListener { task ->
                    Log.d("yes", "строчка 44 файл введения смс кода")
                    if (task.isSuccessful) {
                        Toast.makeText(context, "добро пожаловать ", Toast.LENGTH_SHORT).show()
                        Log.d("yes", "строчка 47 файл введения смс кода")
                        startActivity(Intent(context, MainActivity::class.java))
                    } else {
                        Log.d("yes", "строчка 50 файл введения смс кода")
                        Toast.makeText(
                            context,
                            "У вас случились какието проблемы",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }


}





















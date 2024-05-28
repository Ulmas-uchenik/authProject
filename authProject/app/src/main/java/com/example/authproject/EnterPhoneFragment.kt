package com.example.authproject

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.authproject.databinding.FragmentEnterPhoneBinding
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit

class EnterPhoneFragment : Fragment() {
    private val viewModel: RegistryViewModel by viewModels()

    private lateinit var mCallback:PhoneAuthProvider.OnVerificationStateChangedCallbacks
    private lateinit var phoneNumber: String


    private var _binding: FragmentEnterPhoneBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mCallback = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks(){
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                viewModel.auth.signInWithCredential(credential).addOnCompleteListener {task ->
                    if(task.isSuccessful){
                        Toast.makeText(context, "добро пожаловать ", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(context, MainActivity::class.java))
                    } else {
                        Toast.makeText(
                            context,
                            "У вас случились какието проблемы",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }

            override fun onVerificationFailed(p0: FirebaseException) {
                Toast.makeText(context, "onVerivficationsFalied", Toast.LENGTH_SHORT).show()
            }

            override fun onCodeSent(id: String, token: PhoneAuthProvider.ForceResendingToken) {
                val bundle = Bundle()
                viewModel.setCode(phoneNumber, id)
                findNavController().navigate(R.id.action_enterPhoneFragment2_to_enterCodeFragment)
            }
        }
        _binding = FragmentEnterPhoneBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.checkTelephoneNumber.setOnClickListener {
            if (binding.editText.text.isNullOrEmpty()) {
                Toast.makeText(context, "Введите номер тефона верно пожалуйста", Toast.LENGTH_SHORT).show()
            } else {
                phoneNumber = "+7${binding.editText.text}"
                Toast.makeText(context, "ваш номер телефона :$phoneNumber", Toast.LENGTH_SHORT).show()
                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                    phoneNumber,
                    10,
                    TimeUnit.SECONDS,
                    activity as RegisterActivity,
                    mCallback
                )
            }
        }
    }
}
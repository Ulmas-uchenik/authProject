package com.example.myapplication.ui.menu.profile

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import androidx.activity.addCallback
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.myapplication.MainActivity
import com.example.myapplication.R
import com.example.myapplication.UserViewModel
import com.example.myapplication.UserViewModelFactory
import com.example.myapplication.databinding.FragmentProfileBinding
import com.example.myapplication.ui.auth.AuthActivity
import com.example.myapplication.ui.auth.AuthViewModel
import com.example.myapplication.ui.auth.AuthViewModelFactory
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: UserViewModelFactory
    private val viewModel: UserViewModel by activityViewModels { viewModelFactory }

    @Inject
    lateinit var authViewModelFactory: AuthViewModelFactory
    private val authViewModel: AuthViewModel by viewModels { authViewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dialogBinding = layoutInflater.inflate(R.layout.dialog_conformed_phone, null)
        val myDialog = Dialog(binding.root.context)
        myDialog.setContentView(dialogBinding)
        myDialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
        )
        myDialog.setCancelable(true)
        myDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        setAllText()

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            findNavController().navigate(R.id.action_profileFragment_to_navigation_menu)
        }

        binding.backButtonToolbar.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_navigation_menu)
        }

        binding.saveButton.setOnClickListener {
            Snackbar.make(it, "TestMessage", Snackbar.LENGTH_LONG)
                .setBackgroundTint(Color.parseColor("#FFD85959"))
                .show()

        }

        binding.exitButton.setOnClickListener {
            authViewModel.logout()
            activity?.finish()
            startActivity(Intent(binding.root.context, AuthActivity::class.java))
        }

        binding.conformedTelephoneButton.setOnClickListener {
            myDialog.show()

            val backButton = dialogBinding.findViewById<ImageButton>(R.id.back_button)
            backButton.setOnClickListener {
                myDialog.dismiss()
            }

        }

    }

    private fun setAllText() {
        val selfInfo = viewModel.selfInfo.value
        Log.d("yes", "self info -> ${selfInfo.toString()}")
        if (selfInfo != null) {
            binding.fioEditText.setText("${selfInfo.login}")
            binding.editTextBirthday.setText("${selfInfo.birthDay}")
            binding.fioTextView.text = selfInfo.login

            binding.editTextTelephoneNumber.setText(selfInfo.phone)
            binding.conformedTelephoneButton.visibility = if(selfInfo.phoneConformed == "0") View.VISIBLE else View.GONE

        }
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
package com.example.myapplication.ui.menu.profile

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.UserViewModel
import com.example.myapplication.UserViewModelFactory
import com.example.myapplication.databinding.FragmentProfileBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: UserViewModelFactory
    private val viewModel: UserViewModel by activityViewModels { viewModelFactory }

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

        setAllText()

        binding.editTextPass.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                val text = format(binding.editTextPass.text.toString())
                binding.editTextPass.
                binding.editTextPass.setText(text)
            }
        })

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            findNavController().navigate(R.id.action_profileFragment_to_navigation_menu)
        }

        binding.backButtonToolbar.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_navigation_menu)
        }

    }

    fun setAllText() {
        val selfInfo = viewModel.selfInfo.value
        Log.d("yes", "self info -> ${selfInfo.toString()}")
        if (selfInfo != null) {
            binding.fioEditText.setText("${selfInfo.login}")
            binding.editTextBirthday.setText("${selfInfo.birthDay}")
            binding.fioTextView.text = selfInfo.login
        }
    }

    private fun format(text: String): String{
        val validChar = "1234567890"
        val textWithValidChar: StringBuilder = StringBuilder("")
        for (i in text.indices) {
            if (validChar.contains(text[i])) textWithValidChar.append(text[i])
        }

        val resultText = StringBuilder("")

        for(i in textWithValidChar.indices) {
            when (i) {
                0 -> resultText.append("+7 (${textWithValidChar[i]}")
                2 -> resultText.append("${textWithValidChar[i]}) ")
                6 -> resultText.append("-${textWithValidChar[i]}")
                8 -> resultText.append("-${textWithValidChar[i]}")
                else -> resultText.append(textWithValidChar[i])
            }
        }
        return resultText.toString()
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
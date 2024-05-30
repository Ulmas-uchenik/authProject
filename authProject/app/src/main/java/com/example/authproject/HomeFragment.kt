package com.example.authproject

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.authproject.auth.AuthActivity
import com.example.authproject.databinding.FragmentHomeBinding
class HomeFragment : Fragment() {
    private val viewModel: MainViewModel by viewModels()

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.exitInAccountButton.setOnClickListener {
            viewModel.signOutFromAccount()
            startActivity(Intent(context, AuthActivity::class.java))
            Intent.FLAG_ACTIVITY_CLEAR_TOP
        }
    }
}
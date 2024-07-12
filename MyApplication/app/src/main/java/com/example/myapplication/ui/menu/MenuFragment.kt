package com.example.myapplication.ui.menu

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentMenuBinding
import com.google.android.material.snackbar.Snackbar

class MenuFragment : Fragment() {
    private var _binding: FragmentMenuBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMenuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding){
            navigationView.setNavigationItemSelectedListener {
                when(it.itemId) {
                    R.id.make_an_appointment -> snack(it.title)
                    R.id.doctors_and_services -> findNavController().navigate(R.id.action_navigation_menu_to_doctorAndServicesFragment)
                    R.id.notifications -> findNavController().navigate(R.id.action_navigation_menu_to_notificationsFragment)
                    R.id.magazine_appointments -> snack(it.title)
                    R.id.personal_room -> findNavController().navigate(R.id.action_navigation_menu_to_profileFragment)
                }
                true
            }
        }
    }

    private fun snack(text: CharSequence?){
        Snackbar.make(binding.navigationView, text.toString(), Snackbar.LENGTH_LONG)
            .show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
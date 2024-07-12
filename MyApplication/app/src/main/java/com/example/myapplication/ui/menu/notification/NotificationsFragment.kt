package com.example.myapplication.ui.menu.notification

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.UserViewModel
import com.example.myapplication.UserViewModelFactory
import com.example.myapplication.databinding.FragmentNotificationsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject
@AndroidEntryPoint
class NotificationsFragment : Fragment() {
    private var _binding: FragmentNotificationsBinding? = null
    private val binding get() = _binding!!
    @Inject
    lateinit var viewModelFactory: UserViewModelFactory
    private val viewModel: UserViewModel by activityViewModels { viewModelFactory }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = NotificationListAdapter()
        binding.recyclerView.adapter = adapter
        adapter.setData(viewModel.notification.value)

        binding.backButtonToolbar.setOnClickListener {
            findNavController().navigate(R.id.action_notificationsFragment_to_navigation_menu)
        }

        lifecycleScope.launch {
            viewModel.notification.collect {
                adapter.setData(it)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.pointAllNotificationAdRead()
        _binding = null
    }
}
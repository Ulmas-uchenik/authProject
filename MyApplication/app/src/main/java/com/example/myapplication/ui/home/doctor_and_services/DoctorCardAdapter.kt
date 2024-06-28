package com.example.myapplication.ui.home.doctor_and_services

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import com.example.myapplication.databinding.DoctorCardItemBinding

class DoctorCardAdapter: RecyclerView.Adapter<DoctorCardViewHolder>() {
//    private val values = emptyList<>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DoctorCardViewHolder {
        val binding = DoctorCardItemBinding.inflate(LayoutInflater.from(parent.context))
        return DoctorCardViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DoctorCardViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }
}

class DoctorCardViewHolder(val binding: DoctorCardItemBinding) : RecyclerView.ViewHolder(binding.root)
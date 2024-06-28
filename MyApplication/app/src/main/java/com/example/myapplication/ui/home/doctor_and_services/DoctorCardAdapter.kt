package com.example.myapplication.ui.home.doctor_and_services

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.data.models.Doctor
import com.example.myapplication.databinding.DoctorCardItemBinding

class DoctorCardAdapter : RecyclerView.Adapter<DoctorCardViewHolder>() {
    private var values = emptyList<Doctor>()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(doctorList: List<Doctor>) {
        values = doctorList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DoctorCardViewHolder {
        val binding = DoctorCardItemBinding.inflate(LayoutInflater.from(parent.context))
        return DoctorCardViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DoctorCardViewHolder, position: Int) {
        val item = values[position]
        with(holder.binding) {
            fioTextView.text = item.fio
            majorTextView.text = item.speciality

            if(position == values.size - 1)
                bottomLine.setBackgroundColor(ContextCompat.getColor(majorTextView.context, R.color.empty))
            else
                bottomLine.setBackgroundColor(ContextCompat.getColor(majorTextView.context, R.color.little_black))
        }
            Glide.with(holder.binding.doctorImageView.context)
                .load(item.photo)
                .into(holder.binding.doctorImageView)
    }

    override fun getItemCount(): Int = values.size
}

class DoctorCardViewHolder(val binding: DoctorCardItemBinding) :
    RecyclerView.ViewHolder(binding.root)
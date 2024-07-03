package com.example.myapplication.ui.home.doctor_and_services

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.data.models.Doctor
import com.example.myapplication.databinding.ItemDoctorBinding

class DoctorAdapter (
    private val onDoctorClick: (String) -> Unit
) : RecyclerView.Adapter<DoctorViewHolder>() {
    private var values = emptyList<Doctor>()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(doctorList: List<Doctor>) {
        values = doctorList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DoctorViewHolder {
        val binding = ItemDoctorBinding.inflate(LayoutInflater.from(parent.context))
        return DoctorViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DoctorViewHolder, position: Int) {
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

        holder.binding.root.setOnClickListener {
            onDoctorClick(values[position].id)
        }
    }

    override fun getItemCount(): Int = values.size
}

class DoctorViewHolder(val binding: ItemDoctorBinding) :
    RecyclerView.ViewHolder(binding.root)
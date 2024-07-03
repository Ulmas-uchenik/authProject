package com.example.myapplication.ui.appointment.step2

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.data.models.Services
import com.example.myapplication.databinding.ItemDoctorServicesBinding

class DoctorServicesListAdapter(
//    private val onClick: (String) -> Unit
): RecyclerView.Adapter<DoctorServicesListViewHolder>() {
    private var values = emptyList<Services>()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(doctorServicesList: List<Services>) {
        values = doctorServicesList
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DoctorServicesListViewHolder {
        val binding = ItemDoctorServicesBinding.inflate(LayoutInflater.from(parent.context))
        return DoctorServicesListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DoctorServicesListViewHolder, position: Int) {
        val item = values[position]
        with(holder.binding) {
            doctorTextView.text = item.description
            priceTextView.text = "${item.price} ₽"
            if(position == values.size - 1)
                bottomLine.setBackgroundColor(ContextCompat.getColor(nextButton.context, R.color.empty))
             else
                bottomLine.setBackgroundColor(ContextCompat.getColor(nextButton.context, R.color.empty))
            Glide.with(imageView.context)
                .load("https://cdn-icons-png.flaticon.com/512/5604/5604732.png")
                .into(imageView)
        }
//        holder.binding.nextButton.setOnClickListener {
//            onClick(holder.binding.doctorTextView.text.toString())
//        }
    }

    override fun getItemCount(): Int = values.size
}

class DoctorServicesListViewHolder(val binding: ItemDoctorServicesBinding) : RecyclerView.ViewHolder(binding.root)
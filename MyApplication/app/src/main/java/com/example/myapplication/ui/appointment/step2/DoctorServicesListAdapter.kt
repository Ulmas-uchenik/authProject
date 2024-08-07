package com.example.myapplication.ui.appointment.step2

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.data.models.Services
import com.example.myapplication.databinding.ItemDoctorServicesBinding

class DoctorServicesListAdapter(
    private val onClick: () -> Unit
): RecyclerView.Adapter<DoctorServicesListViewHolder>() {
    private var values = emptyList<Services>()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(doctorServicesList: List<Services>) {
        values = doctorServicesList
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DoctorServicesListViewHolder {
        val binding = ItemDoctorServicesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DoctorServicesListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DoctorServicesListViewHolder, position: Int) {
        val item = values[position]
        with(holder.binding) {
            doctorTextView.text = item.description
            priceTextView.text = "${item.price} ₽"
//            if(position == values.size - 1)
//                bottomLine.setBackgroundColor(ContextCompat.getColor(nextButton.context, R.color.empty))
//             else
//                bottomLine.setBackgroundColor(ContextCompat.getColor(nextButton.context, R.color.little_black))
//            Glide.with(imageView.context)
//                .load("https://cdn-icons-png.flaticon.com/512/5604/5604732.png")
//                .into(imageView)
        }
        holder.binding.nextButton.setOnClickListener {
            onClick()
        }
        holder.binding.root.setOnClickListener {
            onClick()
        }
    }

    override fun getItemCount(): Int = values.size
}

class DoctorServicesListViewHolder(val binding: ItemDoctorServicesBinding) : RecyclerView.ViewHolder(binding.root)
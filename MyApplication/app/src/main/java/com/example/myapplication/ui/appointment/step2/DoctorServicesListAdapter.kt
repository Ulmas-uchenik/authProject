package com.example.myapplication.ui.appointment.step2

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.data.DoctorServices
import com.example.myapplication.databinding.DoctorServicesItemBinding

class DoctorServicesListAdapter(
//    private val onClick: (String) -> Unit
): RecyclerView.Adapter<DoctorServicesListViewHolder>() {
    private var values = emptyList<DoctorServices>()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(doctorServicesList: List<DoctorServices>) {
        values = doctorServicesList.toMutableList()
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DoctorServicesListViewHolder {
        val binding = DoctorServicesItemBinding.inflate(LayoutInflater.from(parent.context))
        return DoctorServicesListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DoctorServicesListViewHolder, position: Int) {
        val item = values[position]
        with(holder.binding) {
            doctorTextView.text = item.service
            priceTextView.text = "${item.price} ₽"
            if(position == values.size - 1){
                bottomLine.setBackgroundColor(ContextCompat.getColor(nextButton.context, R.color.empty))
            }
            Glide.with(imageView.context)
                .load(item.imageSrc)
                .into(imageView)
        }
//        holder.binding.nextButton.setOnClickListener {
//            onClick(holder.binding.doctorTextView.text.toString())
//        }
    }

    override fun getItemCount(): Int = values.size
}

class DoctorServicesListViewHolder(val binding: DoctorServicesItemBinding) : RecyclerView.ViewHolder(binding.root)
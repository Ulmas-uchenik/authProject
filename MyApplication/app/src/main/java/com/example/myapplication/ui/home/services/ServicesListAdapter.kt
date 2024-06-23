package com.example.myapplication.ui.home.services

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.databinding.ServiceItemBinding

class ServicesListAdapter : RecyclerView.Adapter<ServiceListViewHolder>() {
    private var values = emptyList<ServiceForRecyclerView>()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(serviceForRecyclerView: List<ServiceForRecyclerView>) {
        values = serviceForRecyclerView
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServiceListViewHolder {
        val binding = ServiceItemBinding.inflate(LayoutInflater.from(parent.context))
        return ServiceListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ServiceListViewHolder, position: Int) {
        val item = values[position]
        with(holder.binding){
            imageView.setImageResource(item.img)
            doctorTextView.text = item.title
            if(values.size - 1 == position)
                bottomLine.setBackgroundColor(ContextCompat.getColor(nextButton.context, R.color.empty))
            else
                bottomLine.setBackgroundColor(ContextCompat.getColor(nextButton.context, R.color.little_black))
        }
    }

    override fun getItemCount(): Int = values.size
}

class ServiceListViewHolder(val binding: ServiceItemBinding) : RecyclerView.ViewHolder(binding.root)
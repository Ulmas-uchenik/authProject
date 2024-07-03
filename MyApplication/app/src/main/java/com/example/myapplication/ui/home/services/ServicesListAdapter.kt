package com.example.myapplication.ui.home.services

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.databinding.ItemServicesBinding

class ServicesListAdapter : RecyclerView.Adapter<ServicesListViewHolder>() {
    private var values = emptyList<ServicesForRecyclerView>()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(serviceForRecyclerView: List<ServicesForRecyclerView>) {
        values = serviceForRecyclerView
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServicesListViewHolder {
        val binding = ItemServicesBinding.inflate(LayoutInflater.from(parent.context))
        return ServicesListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ServicesListViewHolder, position: Int) {
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

class ServicesListViewHolder(val binding: ItemServicesBinding) : RecyclerView.ViewHolder(binding.root)
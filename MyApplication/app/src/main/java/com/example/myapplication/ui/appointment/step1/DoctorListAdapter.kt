package com.example.myapplication.ui.appointment.step1

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.data.models.Categories
import com.example.myapplication.data.models.CategoriesList
import com.example.myapplication.databinding.DoctorsItemBinding

class DoctorListAdapter(
    private val onClick: (String, String) -> Unit
): RecyclerView.Adapter<DoctorListViewHolder>() {
    private var values = emptyList<Categories>()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(doctorList: List<Categories>) {
        values = doctorList
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DoctorListViewHolder {
        val binding = DoctorsItemBinding.inflate(LayoutInflater.from(parent.context))
        return DoctorListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DoctorListViewHolder, position: Int) {
        val item = values[position].name
        with(holder.binding) {
            doctorTextView.text = item
            if(position == values.size - 1){
                bottomLine.setBackgroundColor(ContextCompat.getColor(nextButton.context, R.color.empty))
            } else {
                bottomLine.setBackgroundColor(ContextCompat.getColor(nextButton.context, R.color.little_black))
            }
        }
        holder.binding.nextButton.setOnClickListener {
            onClick(values[position].id, values[position].name)
        }
    }

    override fun getItemCount(): Int = values.size
}

class DoctorListViewHolder(val binding: DoctorsItemBinding) : RecyclerView.ViewHolder(binding.root)
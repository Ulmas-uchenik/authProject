package com.example.myapplication.ui.appointment

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.databinding.DoctorsItemBinding

class DoctorListAdapter(
    private val onClick: (String) -> Unit
): RecyclerView.Adapter<DoctorListViewHolder>() {
    private var values = emptyList<String>()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(doctorList: List<String>) {
        values = doctorList.toMutableList()
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DoctorListViewHolder {
        val binding = DoctorsItemBinding.inflate(LayoutInflater.from(parent.context))
        return DoctorListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DoctorListViewHolder, position: Int) {
        val item = values[position]
        with(holder.binding) {
            doctorTextView.text = item
            if(position == values.size - 1){
                bottomLine.setBackgroundColor(ContextCompat.getColor(nextButton.context, R.color.empty))
            }
        }
        holder.binding.nextButton.setOnClickListener {
            onClick(holder.binding.doctorTextView.text.toString())
        }
    }

    override fun getItemCount(): Int = values.size
}

class DoctorListViewHolder(val binding: DoctorsItemBinding) : RecyclerView.ViewHolder(binding.root)
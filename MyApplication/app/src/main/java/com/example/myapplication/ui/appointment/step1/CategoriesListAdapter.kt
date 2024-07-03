package com.example.myapplication.ui.appointment.step1

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.data.models.Categories
import com.example.myapplication.databinding.ItemCategoriesBinding

class CategoriesListAdapter(
    private val onClick: (String, String) -> Unit
): RecyclerView.Adapter<CategoriesListViewHolder>() {
    private var values = emptyList<Categories>()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(doctorList: List<Categories>) {
        values = doctorList
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesListViewHolder {
        val binding = ItemCategoriesBinding.inflate(LayoutInflater.from(parent.context))
        return CategoriesListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoriesListViewHolder, position: Int) {
        val item = values[position].name
        with(holder.binding) {
            doctorTextView.text = item
            if(position == values.size - 1)
                bottomLine.setBackgroundColor(ContextCompat.getColor(nextButton.context, R.color.empty))
             else
                bottomLine.setBackgroundColor(ContextCompat.getColor(nextButton.context, R.color.little_black))

        }
        holder.binding.nextButton.setOnClickListener {
            onClick(values[position].id, values[position].name)
        }
    }

    override fun getItemCount(): Int = values.size
}

class CategoriesListViewHolder(val binding: ItemCategoriesBinding) : RecyclerView.ViewHolder(binding.root)
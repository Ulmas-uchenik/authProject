package com.example.myapplication.ui.menu.notification

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.data.models.NotificationsList
import com.example.myapplication.databinding.ItemNotificationBinding

class NotificationListAdapter : RecyclerView.Adapter<NotificationListViewHolder>() {
    private var values = emptyList<NotificationsList.Notification>()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(notificationList: List<NotificationsList.Notification>) {
        values = notificationList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationListViewHolder {
        val binding = ItemNotificationBinding.inflate(LayoutInflater.from(parent.context))
        return NotificationListViewHolder(binding)
    }


    override fun onBindViewHolder(holder: NotificationListViewHolder, position: Int) {
        val item = values[position]
        with(holder.binding) {
            contentTextView.text = item.text
            if (item.id.toInt() % 2 == 0) isReadedView.setBackgroundColor(
                ContextCompat.getColor(
                    holder.binding.isReadedView.context, R.color.empty
                )
            )
        }
    }

    override fun getItemCount(): Int = values.size
}

class NotificationListViewHolder(val binding: ItemNotificationBinding) :
    RecyclerView.ViewHolder(binding.root)
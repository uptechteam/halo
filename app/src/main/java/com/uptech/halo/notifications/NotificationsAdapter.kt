package com.uptech.halo.notifications

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.uptech.halo.databinding.ItemNotificationBinding

class NotificationsAdapter(
  private val items: MutableList<NotificationItem>
)  : RecyclerView.Adapter<NotificationsViewHolder>(){
  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationsViewHolder {
    return NotificationsViewHolder(ItemNotificationBinding.inflate(LayoutInflater.from(parent.context), parent, false))
  }

  override fun onBindViewHolder(holder: NotificationsViewHolder, position: Int) {
    holder.binding.notificationText.text = items[position].message
  }

  override fun getItemCount(): Int {
    return items.size
  }
}


class NotificationsViewHolder(
  val binding: ItemNotificationBinding
) : RecyclerView.ViewHolder(binding.root)

data class NotificationItem(
  val message: String
)
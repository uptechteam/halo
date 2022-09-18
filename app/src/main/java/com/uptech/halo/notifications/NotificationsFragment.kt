package com.uptech.halo.notifications

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.uptech.halo.R
import com.uptech.halo.databinding.FragmentNotificationsBinding

class NotificationsFragment : Fragment(R.layout.fragment_notifications) {

  private lateinit var binding: FragmentNotificationsBinding

  private val adapter = NotificationsAdapter(
    mutableListOf(
      NotificationItem("We contacted the author of your reward #12341, waiting while he is preparing it for you"),
      NotificationItem("Hey, You have enough points to spend on something interesting"),
      NotificationItem("We just added new reward to our shop, look how it is amazing"),
      NotificationItem("Our reward is ready, please check your email. We've sent an invitation there"),
    )
  )

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    binding = FragmentNotificationsBinding.bind(view)
    binding.notificationsList.adapter = adapter
    binding.notificationsList.layoutManager = LinearLayoutManager(requireContext())
  }
}
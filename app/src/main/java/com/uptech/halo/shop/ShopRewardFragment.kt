package com.uptech.halo.shop

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.uptech.halo.R
import com.uptech.halo.databinding.FragmentShopRewardBinding

class ShopRewardFragment : Fragment(R.layout.fragment_shop_reward) {

  private lateinit var binding: FragmentShopRewardBinding

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    binding = FragmentShopRewardBinding.bind(view)
  }
}
package com.uptech.halo.shop

import android.os.Bundle
import android.text.Html
import android.text.method.LinkMovementMethod
import android.text.method.MovementMethod
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.uptech.halo.R
import com.uptech.halo.databinding.FragmentShopRewardBinding
import com.uptech.halo.models.ServiceShopItem
import com.uptech.halo.models.ShopItem

class ShopRewardFragment : Fragment(R.layout.fragment_shop_reward) {

  private lateinit var binding: FragmentShopRewardBinding

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    binding = FragmentShopRewardBinding.bind(view)

    initData()
  }

  private fun initData() {
    val item = arguments?.getParcelable<ServiceShopItem>(KEY) ?: return
    binding.apply {
      stepsForReward.movementMethod = LinkMovementMethod()
      stepsForReward.text = Html.fromHtml(item.reward.description)
      close.setOnClickListener {
        findNavController().popBackStack()
      }
    }
  }

  companion object {

    private const val KEY = "asfa"
    fun newBundle(item: ServiceShopItem) = Bundle().apply {
      putParcelable(KEY, item)
    }
  }
}
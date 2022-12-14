package com.uptech.halo.shop

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.shape.CornerFamily
import com.uptech.halo.R
import com.uptech.halo.data.FirebaseDataSource
import com.uptech.halo.databinding.FragmentShopItemDetailsBinding
import com.uptech.halo.models.ServiceShopItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ShopItemDetailsFragment : Fragment(R.layout.fragment_shop_item_details) {

  private lateinit var binding: FragmentShopItemDetailsBinding

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    binding = FragmentShopItemDetailsBinding.bind(view)

    binding.productImage.shapeAppearanceModel = binding.productImage.shapeAppearanceModel
      .toBuilder()
      .setBottomRightCorner(CornerFamily.ROUNDED, 26f)
      .setBottomLeftCorner(CornerFamily.ROUNDED, 26f)
      .build()


    val item = arguments?.getParcelable<ServiceShopItem>(KEY)
    item?.let { initData(it) }
  }

  private fun initData(item: ServiceShopItem) {
    val glide = Glide.with(this)
    binding.apply {
      glide.load(item.imageUrl).into(productImage)
      glide.load(item.author.imageUrl).into(authorImage)
      productName.text = item.title
      productDescription.text = item.description
      authorName.text = item.author.name
      authorCompany.text = item.author.company
      buy.text = "Redeem for ${item.price}"
      buy.setOnClickListener {
        lifecycleScope.launch(Dispatchers.IO) {
          FirebaseDataSource.updateDonatorUserBalance(requireContext(), -item.price)
          withContext(Dispatchers.Main) {
            findNavController().navigate(R.id.action_shopItemDetailsFragment_to_shopRewardFragment,
              ShopRewardFragment.newBundle(item))
          }
        }
      }
      share.setOnClickListener {
        val url = "https://halo.uptech.com/reward/${item.id}"
        val sendIntent: Intent = Intent().apply {
          action = Intent.ACTION_SEND
          putExtra(Intent.EXTRA_TEXT, url)
          type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
      }

      close.setOnClickListener {
        findNavController().popBackStack()
      }
    }

    lifecycleScope.launch(Dispatchers.IO) {
      val balance = FirebaseDataSource.getDonatorUser(requireContext()).balance
      withContext(Dispatchers.Main) {
        binding.buy.isEnabled = item.price <= balance
      }
    }
  }

  companion object {
    private const val KEY = "safasf"
    fun newBundle(shopItem: ServiceShopItem) = Bundle().apply {
      putParcelable(KEY, shopItem)
    }
  }
}
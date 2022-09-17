package com.uptech.halo.shop

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.uptech.halo.R
import com.uptech.halo.data.FirebaseDataSource
import com.uptech.halo.databinding.FragmentShopBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ShopFragment : Fragment(R.layout.fragment_shop) {
  private lateinit var binding: FragmentShopBinding

  private lateinit var glide: RequestManager
  private lateinit var adapter: ShopAdapter

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    binding = FragmentShopBinding.bind(view)

    glide = Glide.with(this)
    adapter = ShopAdapter(mutableListOf(), glide)

    binding.shopItemsRv.layoutManager = GridLayoutManager(requireContext(), 2)
    binding.shopItemsRv.adapter = adapter

    initData()
  }

  private fun initData() {
    lifecycleScope.launch(Dispatchers.IO) {
      val shopItems = FirebaseDataSource.getAllShopItems()
      withContext(Dispatchers.Main) {
        adapter.swapItems(shopItems)
      }
    }

    lifecycleScope.launch(Dispatchers.IO) {
      val user = FirebaseDataSource.getDonatorUser(GoogleSignIn.getLastSignedInAccount(requireContext())?.id.toString())
      withContext(Dispatchers.Main) {
        binding.balance.text = user.balance.toString()
      }
    }
  }
}
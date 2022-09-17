package com.uptech.halo.shop

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.uptech.halo.databinding.ItemShopBinding
import com.uptech.halo.models.ShopItem

class ShopAdapter(
  private val items: MutableList<ShopItem>,
  private val glide: RequestManager
) : RecyclerView.Adapter<ShopItemViewHolder>() {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopItemViewHolder {
    return ShopItemViewHolder(ItemShopBinding.inflate(LayoutInflater.from(parent.context), parent, false), glide)
  }

  override fun onBindViewHolder(holder: ShopItemViewHolder, position: Int) {
    holder.bind(items[position])
  }

  override fun getItemCount(): Int {
    return items.size
  }

  fun swapItems(newItems: List<ShopItem>) {
    items.clear()
    items.addAll(newItems)
    notifyDataSetChanged()
  }
}

class ShopItemViewHolder(
  private val binding: ItemShopBinding,
  private val glide: RequestManager
) : RecyclerView.ViewHolder(binding.root) {

  fun bind(item: ShopItem) {
    binding.apply {
      itemName.text = item.title
      itemPrice.text = item.price.toString()
      glide.load(item.imageUrl)
        .override(1024, 758)
        .into(itemImage)
    }
  }
}
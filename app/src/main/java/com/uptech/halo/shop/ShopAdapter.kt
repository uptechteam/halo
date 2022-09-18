package com.uptech.halo.shop

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.shape.CornerFamily
import com.uptech.halo.databinding.ItemShopBinding
import com.uptech.halo.dp
import com.uptech.halo.models.ShopItem

class ShopAdapter(
  private val items: MutableList<ShopItem>,
  private val glide: RequestManager,
  private val onCLick: (ShopItem) -> Unit
) : RecyclerView.Adapter<ShopItemViewHolder>() {

  private var balance: Long = 0

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopItemViewHolder {
    return ShopItemViewHolder(ItemShopBinding.inflate(LayoutInflater.from(parent.context), parent, false).apply {
      itemImage.shapeAppearanceModel = itemImage.shapeAppearanceModel
        .toBuilder()
        .setTopRightCorner(CornerFamily.ROUNDED, 20f)
        .setTopLeftCorner(CornerFamily.ROUNDED, 20f)
        .build()
    }, glide)
  }

  override fun onBindViewHolder(holder: ShopItemViewHolder, position: Int) {
    holder.bind(items[position], balance)
    holder.itemView.setOnClickListener {
      onCLick.invoke(items[position])
    }
  }

  override fun getItemCount(): Int {
    return items.size
  }

  fun setBalance(balance: Long) {
    this.balance = balance
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

  fun bind(item: ShopItem, balance: Long) {
    binding.apply {
      itemName.text = item.title
      itemPrice.text = item.price.toString()
      itemImage.setShapeAppearanceModel(
        itemImage.getShapeAppearanceModel()
          .toBuilder()
          .setTopRightCorner(CornerFamily.ROUNDED, 5f.dp())
          .setTopLeftCorner(CornerFamily.ROUNDED, 5f.dp())
          .build()

      )
      glide.load(item.imageUrl)
        .override(500, 758)
        .fitCenter()
        .into(itemImage)
      val alpha = if (balance >= item.price) 1f else 0.5f
      binding.itemPrice.alpha = alpha
    }
  }
}
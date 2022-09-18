package com.uptech.halo.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

interface ShopItem : Parcelable {
  val id: String
  val title: String
  val description: String
  val price: Long
  val author: User
  val reward: Reward
  val imageUrl: String
}

@Parcelize
data class ServiceShopItem(
  override val id: String,
  override val title: String,
  override val description: String,
  override val price: Long,
  override val author: PartnerUser,
  override val reward: Reward,
  override val imageUrl: String,
) : ShopItem {
  constructor() : this(
    "",
    "",
    "",
    0L,
    PartnerUser("", "", "", "", ""),
    InstructionReward("", "", "", ""),
    ""
  )
}


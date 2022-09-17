package com.uptech.halo.models

interface ShopItem {
  val id: String
  val title: String
  val description: String
  val price: Long
  val author: User
  val reward: Reward
}

data class ServiceShopItem(
  override val id: String,
  override val title: String,
  override val description: String,
  override val price: Long,
  override val author: PartnerUser,
  override val reward: Reward,
) : ShopItem {
  constructor() : this(
    "",
    "",
    "",
    0L,
    PartnerUser("", "", "", emptyList()),
    InstructionReward("", "", "", emptyList())
  )
}


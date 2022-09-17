package com.uptech.halo.models

interface User {
  val id: String
  val name: String
  val email: String
}

data class DonatorUser(
  override val id: String,
  override val name: String,
  override val email: String,
  val icon: String,
  val balance: Long
) : User {
  constructor() : this(
    "",
    "",
    "",
    "",
    0L
  )
}

data class CollectorUser(
  override val id: String,
  override val name: String,
  override val email: String,
) : User {
  constructor() : this(
    "",
    "",
    "",
  )
}

data class PartnerUser(
  override val id: String,
  override val name: String,
  override val email: String,
  val shopItems: List<ShopItem>
) : User {
  constructor() : this(
    "",
    "",
    "",
    emptyList()
  )
}




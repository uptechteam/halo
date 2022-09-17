package com.uptech.halo.models

data class Wallet(
  val id: String,
  val user: User,
  val balance: Long
)
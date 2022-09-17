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
  val balance: Long
) : User
data class CollectorUser(override val id: String, override val name: String, override val email: String) : User
data class PartnerUser(override val id: String, override val name: String, override val email: String) : User




package com.uptech.halo.models

interface Fund {
  val id: String
  val name: String
}

data class CharityFund(
  override val id: String,
  override val name: String,
  val iban: String
) : Fund

data class MonoBank(
  override val id: String,
  override val name: String,
  val bankNumber: String
) : Fund
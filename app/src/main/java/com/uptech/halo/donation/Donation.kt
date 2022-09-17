package com.uptech.halo.donation

data class Donation(
  val id: Int,
  val imageUrl: String,
  val title: CharSequence,
  val progress: Pair<Int, Int>
)
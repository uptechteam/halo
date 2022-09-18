package com.uptech.halo.donation.details

import java.io.Serializable

data class Donation(
  val id: Int,
  val imageUrl: String,
  val title: CharSequence,
  val progress: Pair<Int, Int>,
  val description: CharSequence
) : Serializable
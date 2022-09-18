package com.uptech.halo.donation.details

import java.io.Serializable

data class Donation(
  val id: String,
  val imageUrl: String,
  val title: CharSequence,
  val progress: Pair<Long, Long>,
  val description: CharSequence
) : Serializable
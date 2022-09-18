package com.uptech.halo.donation

import java.io.Serializable

data class Donation(
  val id: String,
  val imageUrl: String,
  val title: CharSequence,
  val progress: Pair<Long, Long>
) : Serializable
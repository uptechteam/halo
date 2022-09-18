package com.uptech.halo.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

interface Reward : Parcelable {
  val id: String
  val name: String
  val description: String
}

@Parcelize
data class InstructionReward(
  override val id: String,
  override val name: String,
  override val description: String,
  val stepsToReceive: String
) : Reward {
  constructor() : this(
    "",
    "",
    "",
    ""
  )
}
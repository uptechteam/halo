package com.uptech.halo.models

interface Reward {
  val id: String
  val name: String
  val description: String
}

data class InstructionReward(
  override val id: String,
  override val name: String,
  override val description: String,
  val stepsToReceive: List<String>
) : Reward {
  constructor() : this(
    "",
    "",
    "",
    emptyList()
  )
}
package com.uptech.halo.models

data class Lot(
  val id: String,
  val title: String,
  val target: Long,
  val targetDescription: String,
  val collectorUser: CollectorUser,
  val fund: Fund
)
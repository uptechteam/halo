package com.uptech.halo.models

data class Lot(
  val id: String,
  val title: String,
  val target: Long,
  val targetDescription: String,
  val collectorUser: CollectorUser,
  val fund: Fund,
) {
  constructor() : this(
    "",
    "",
    0L,
    "",
    CollectorUser(
      "",
      "",
      "",
    ),
    Fund(
      "",
      "",
      "",
      FundType.MONO_JAR
    )
  )
}

data class Donation(
  val user: DonatorUser,
  val lot: Lot
) {
  constructor() : this(
    DonatorUser(
      "",
      "",
      "",
      "",
      0L
    ),
    Lot(
      "",
      "",
      0L,
      "",
      CollectorUser(
        "",
        "",
        "",
      ),
      Fund(
        "",
        "",
        "",
        FundType.MONO_JAR

      )
    )
  )
}
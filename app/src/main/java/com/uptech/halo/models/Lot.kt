package com.uptech.halo.models

data class Lot(
  val id: String,
  val title: String,
  val imageUrl: String,
  val target: Long,
  val progress: Long,
  val targetDescription: String,
  val collectorUser: CollectorUser,
  val fund: Fund,
) {
  constructor() : this(
    "",
    "",
    "",
    0L,
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
      ""/*FundType.MONO_JAR*/
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
      0L,
      ""
    ),
    Lot(
      "",
      "",
      "",
      0L,
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
        ""/*FundType.MONO_JAR*/

      )
    )
  )
}
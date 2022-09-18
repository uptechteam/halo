package com.uptech.halo.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

interface User : Parcelable {
  val id: String
  val name: String
  val email: String
}

@Parcelize
data class DonatorUser(
  override val id: String,
  override val name: String,
  override val email: String,
  val icon: String,
  val balance: Long
) : User {
  constructor() : this(
    "",
    "",
    "",
    "",
    0L
  )
}

@Parcelize
data class CollectorUser(
  override val id: String,
  override val name: String,
  override val email: String,
) : User {
  constructor() : this(
    "",
    "",
    "",
  )
}

@Parcelize
data class PartnerUser(
  override val id: String,
  override val name: String,
  override val email: String,
  val imageUrl: String,
  val company: String
) : User {
  constructor() : this(
    "",
    "",
    "",
    "",
    ""
  )
}




package com.uptech.halo.models

data class Fund(
  val id: String,
  val name: String,
  val bankNumber: String,
  val type: String/*FundType*/
) {
  constructor() : this("", "", "", "CHARITY_FUND"/*FundType.CHARITY_FUND*/)
}

enum class FundType {
  CHARITY_FUND,
  MONO_JAR
}
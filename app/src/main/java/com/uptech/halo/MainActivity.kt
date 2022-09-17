package com.uptech.halo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.uptech.halo.data.FirebaseDataSource
import com.uptech.halo.databinding.ActivityMainBinding
import com.uptech.halo.models.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class MainActivity : AppCompatActivity() {

  private lateinit var binding: ActivityMainBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)
    init()
  }


  fun init() {
    binding.createLot.setOnClickListener {
      saveLot()
    }

    binding.getLots.setOnClickListener {
      getLots()
    }
  }

  private fun saveLot() {
    val collectUser = CollectorUser(
      UUID.randomUUID().toString(),
      "user name",
      "user email",
    )

    val charityFund = Fund(
      UUID.randomUUID().toString(),
      "Charity fund",
      "UA1231242141251254215251245",
      FundType.CHARITY_FUND
    )

    val monoFund = Fund(
      UUID.randomUUID().toString(),
      "Mono",
      "1234 2141 1244 1241",
      FundType.MONO_JAR
    )

    val uuid1 = UUID.randomUUID().toString()
    val uuid2 = UUID.randomUUID().toString()

    lifecycleScope.launch(Dispatchers.IO) {
      FirebaseDataSource.saveLot(
        Lot(
          uuid1,
          "Lot ${uuid1.take(5)}",
          100_000,
          "description",
          collectUser,
          charityFund
        )
      )

      FirebaseDataSource.saveLot(
        Lot(
          uuid2,
          "Lot ${uuid2.take(5)}",
          100_000,
          "description",
          collectUser,
          monoFund
        )
      )
    }
  }

  private fun getLots() {
    lifecycleScope.launch(Dispatchers.IO) {
      val lots = FirebaseDataSource.getAllLots()
      Log.e("APP", "lots: $lots")
    }
  }
}
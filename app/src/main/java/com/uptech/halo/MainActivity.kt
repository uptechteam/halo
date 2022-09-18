package com.uptech.halo

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.uptech.halo.data.FirebaseDataSource
import com.uptech.halo.databinding.ActivityMainBinding
import com.uptech.halo.donation.details.DonationDetailsFragment.Companion.DONATION_ID
import com.uptech.halo.models.ServiceShopItem
import com.uptech.halo.shop.ShopRewardFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

  private lateinit var binding: ActivityMainBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)
    val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
    val navController = navHostFragment.navController
    NavigationUI.setupWithNavController(binding.mainBar, navController)
    when(intent.data?.pathSegments?.first()) {
      "donation" -> intent.data?.pathSegments?.last()?.let { donationId ->
        navController.navigate(R.id.donationDetailsFragment, Bundle().apply { putString(DONATION_ID, donationId) })
      }
      "reward" -> intent.data?.pathSegments?.last()?.let { rewardId ->
        binding.mainBar.selectedItemId = R.id.shopFragment
        lifecycleScope.launch(Dispatchers.IO) {
          FirebaseDataSource.getAllShopItems().find { it.id == rewardId }?.let { shopItem ->
            navController.navigate(R.id.action_shopItemDetailsFragment_to_shopRewardFragment, ShopRewardFragment
              .newBundle(shopItem as ServiceShopItem))
          }
        }
      }
    }
    navController.addOnDestinationChangedListener { _, destination, _ ->
      binding.mainBar.isVisible = when(destination.id) {
        R.id.donationsFragment, R.id.shopFragment -> true
        else -> false
      }
    }
  }

  override fun onStart() {
    super.onStart()
    val account = GoogleSignIn.getLastSignedInAccount(this)
    if(account === null) {
      startActivity(Intent(this, LoginActivity::class.java))
    }
  }
}
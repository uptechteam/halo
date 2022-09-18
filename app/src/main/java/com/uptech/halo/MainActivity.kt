package com.uptech.halo

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.uptech.halo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

  private lateinit var binding: ActivityMainBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)
    val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
    val navController = navHostFragment.navController
    NavigationUI.setupWithNavController(binding.mainBar, navController)
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
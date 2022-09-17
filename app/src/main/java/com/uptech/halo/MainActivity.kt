package com.uptech.halo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.uptech.halo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
  private val login = registerForActivityResult(StartActivityForResult()) { }

  private lateinit var binding: ActivityMainBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)
  }

  override fun onStart() {
    super.onStart()
    val account = GoogleSignIn.getLastSignedInAccount(this)
    if(account === null) {
      login.launch(Intent(this, LoginActivity::class.java))
    }
  }
}
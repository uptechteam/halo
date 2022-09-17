package com.uptech.halo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.uptech.halo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

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
      startActivity(Intent(this, LoginActivity::class.java))
    } else {
      initScreen()
    }
  }

  private fun initScreen() {
    binding.sigOut.setOnClickListener {
      val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestEmail()
        .build()
      val googleSignInClient = GoogleSignIn.getClient(this, gso)
      googleSignInClient.signOut()
        .addOnSuccessListener {
          startActivity(Intent(this, LoginActivity::class.java))
          finish()
        }
    }
  }
}
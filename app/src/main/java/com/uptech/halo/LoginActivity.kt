package com.uptech.halo

import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignInOptions.Builder
import com.google.android.gms.common.api.ApiException
import com.uptech.halo.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
  private lateinit var binding: ActivityLoginBinding
  private val googleSignIn = registerForActivityResult(StartActivityForResult()) { result ->
    val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
    try {
      val account = task.getResult(ApiException::class.java)
      finish()
    } catch (e: ApiException) {
      println("error")
    }
  }
  private lateinit var googleSignInClient: GoogleSignInClient

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    ActivityLoginBinding.inflate(layoutInflater)
      .also { binding ->
        this.binding = binding
        setContentView(binding.root)
      }
    binding.signIn.setOnClickListener {
      val gso = Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestEmail()
        .build()
      googleSignInClient = GoogleSignIn.getClient(this, gso)
      googleSignIn.launch(googleSignInClient.signInIntent)
    }
  }
}
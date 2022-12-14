package com.uptech.halo

import android.R
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignInOptions.Builder
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.uptech.halo.data.FirebaseDataSource
import com.uptech.halo.databinding.ActivityLoginBinding
import com.uptech.halo.models.DonatorUser
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class LoginActivity : AppCompatActivity() {
  private lateinit var binding: ActivityLoginBinding
  private val googleSignIn = registerForActivityResult(StartActivityForResult()) { result ->
    val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
    try {
      val account = task.result
      lifecycleScope.launch(Dispatchers.IO) {

        val deffered = CompletableDeferred<String>()
        FirebaseMessaging.getInstance().token
          .addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
              deffered.complete("")
              return@OnCompleteListener
            }
            val token = task.result
            deffered.complete(token)
          })


        if (account.id != null && account.displayName != null && account.email != null) {
          FirebaseDataSource.saveUser(
            DonatorUser(
              account.id!!,
              account.displayName!!,
              account.email!!,
              account.photoUrl.toString(),
              0L,
              deffered.await()
            )
          )
        }
      }
      startActivity(Intent(this, MainActivity::class.java))
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
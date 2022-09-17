package com.uptech.halo

import android.app.Application
import com.google.firebase.FirebaseApp

class HaloApp : Application() {

  override fun onCreate() {
    super.onCreate()
    FirebaseApp.initializeApp(this)
  }
}
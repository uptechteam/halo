package com.uptech.halo.data

import android.content.Context
import android.util.Log
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.uptech.halo.models.*
import kotlinx.coroutines.coroutineScope
import kotlin.coroutines.*

object FirebaseDataSource {

  private val database: FirebaseDatabase = FirebaseDatabase.getInstance()

  private val usersRef: DatabaseReference = database.getReference("users")
  private val lotsRef: DatabaseReference = database.getReference("lots")
  private val shopItemsRef: DatabaseReference = database.getReference("shop_items")
  private val fundsRef: DatabaseReference = database.getReference("funds")

  suspend fun getDonatorUser(context: Context): DonatorUser = suspendCoroutine { cont ->
    val userId = GoogleSignIn.getLastSignedInAccount(context)?.id.toString()
    usersRef.child(userId).get().subscribe(cont) { dataSnapshot ->
      dataSnapshot.getValue(DonatorUser::class.java)!!
    }
  }

  suspend fun updateDonatorUserBalance(context: Context, balance: Long): Unit = coroutineScope {
    val user = getDonatorUser(context)
    suspendCoroutine { cont ->
      usersRef.child(user.id).child("balance").setValue(user.balance + balance).subscribe(cont, Unit)
    }
  }

  suspend fun saveUser(user: User): User = suspendCoroutine { cont ->
    usersRef.child(user.id).setValue(user).subscribe(cont, user)
  }

  suspend fun saveLot(lot: Lot): Lot = suspendCoroutine { cont ->
    lotsRef.child(lot.id).setValue(lot).subscribe(cont, lot)
  }

  suspend fun updateLotProgress(lotId: String, balance: Long) = coroutineScope {
    val currentProgress = suspendCoroutine<Int?> { cont ->
      lotsRef.child(lotId).child("progress").run {
        get().subscribe(cont) { dataSnapshot ->
          dataSnapshot.getValue(Int::class.java)
        }
      }
    }
    suspendCoroutine<Unit> { cont ->
      lotsRef.child(lotId).child("progress")
        .setValue((currentProgress ?: 0) + balance).subscribe(cont, Unit)
    }
  }

  suspend fun getAllLots(): List<Lot> = suspendCoroutine { cont ->
    lotsRef.get().subscribeList(cont) { dataSnapshot ->
      dataSnapshot.children.mapNotNull {
        it.getValue(Lot::class.java)
      }
    }
  }

  suspend fun getAllShopItems(): List<ShopItem> = suspendCoroutine { cont ->
    shopItemsRef.get().subscribeList(cont) { dataSnapshot ->
      dataSnapshot.children.mapNotNull {
        ServiceShopItem(
          it.child("id").value as String,
          it.child("title").value as String,
          it.child("description").value as String,
          it.child("price").value as Long,
          it.child("author").getValue(PartnerUser::class.java)!!,
          it.child("reward").getValue(InstructionReward::class.java)!!,
          it.child("imageUrl").value as String
        )
      }
    }
  }

  suspend fun getAllFunds(): List<Fund> = suspendCoroutine { cont ->
    fundsRef.get().subscribeList(cont) { dataSnapshot ->
      dataSnapshot.children.mapNotNull {
        it.getValue(Fund::class.java)
      }
    }
  }

  fun <T> Task<Void>.subscribe(cont: Continuation<T>, res: T) {
    addOnSuccessListener {
      Log.e("APP", "onSuccess")
      cont.resume(res)
    }.addOnFailureListener {
      Log.e("APP", "onError: $it")
      cont.resumeWithException(it)
    }
  }

  fun <T> Task<DataSnapshot>.subscribeList(cont: Continuation<List<T>>, map: (DataSnapshot) -> List<T>) {
    addOnSuccessListener { dataSnapshot ->
      Log.e("APP", "onSuccess")
      cont.resume(map.invoke(dataSnapshot))
    }.addOnFailureListener {
      Log.e("APP", "onError: $it")
      cont.resumeWithException(it)
    }
  }

  fun <T> Task<DataSnapshot>.subscribe(cont: Continuation<T>, map: (DataSnapshot) -> T) {
    addOnSuccessListener { dataSnapshot ->
      Log.e("APP", "onSuccess")
      cont.resume(map.invoke(dataSnapshot))
    }.addOnFailureListener {
      Log.e("APP", "onError: $it")
      cont.resumeWithException(it)
    }
  }
}
package com.uptech.halo.data

import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.uptech.halo.models.*
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

object FirebaseDataSource {

  private val database: FirebaseDatabase = FirebaseDatabase.getInstance()

  private val usersRef: DatabaseReference = database.getReference("users")
  private val lotsRef: DatabaseReference = database.getReference("lots")
  private val shopItemsRef: DatabaseReference = database.getReference("shop_items")
  private val fundsRef: DatabaseReference = database.getReference("funds")

  suspend fun saveUser(user: User): User = suspendCoroutine { cont ->
    usersRef.child(user.id).setValue(user).subscribe(cont, user)
  }

  suspend fun saveLot(lot: Lot): Lot = suspendCoroutine { cont ->
    lotsRef.child(lot.id).setValue(lot).subscribe(cont, lot)
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
        it.getValue(ServiceShopItem::class.java)
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
}
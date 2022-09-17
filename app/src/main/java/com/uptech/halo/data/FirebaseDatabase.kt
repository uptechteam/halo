package com.uptech.halo.data

import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.uptech.halo.models.*
import java.lang.Exception
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
        try {
          it.getValue(CharityFund::class.java)
        } catch (e: Exception) {
          try {
            it.getValue(MonoBank::class.java)
          } catch (e: Exception) {
            null
          }
        }
      }
    }
  }


  fun <T> Task<Void>.subscribe(cont: Continuation<T>, res: T) {
    addOnSuccessListener {
      cont.resume(res)
    }.addOnFailureListener {
      cont.resumeWithException(it)
    }
  }

  fun <T> Task<DataSnapshot>.subscribeList(cont: Continuation<List<T>>, map: (DataSnapshot) -> List<T>) {
    addOnSuccessListener { dataSnapshot ->
      cont.resume(map.invoke(dataSnapshot))
    }.addOnFailureListener {
      cont.resumeWithException(it)
    }
  }
}
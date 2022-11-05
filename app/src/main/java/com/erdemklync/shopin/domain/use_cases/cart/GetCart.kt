package com.erdemklync.shopin.domain.use_cases.cart

import com.erdemklync.shopin.data.remote.entity.CartItem
import com.erdemklync.shopin.util.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.snapshots
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetCart @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) {
    operator fun invoke(): Flow<List<CartItem>>? {
        firebaseAuth.currentUser?.let { user ->
            return firestore
                .collection(Constants.USERS)
                .document(user.uid)
                .collection(Constants.CART)
                .snapshots()
                .map {
                    it.toObjects(CartItem::class.java)
                }
        }
        return null
    }
}
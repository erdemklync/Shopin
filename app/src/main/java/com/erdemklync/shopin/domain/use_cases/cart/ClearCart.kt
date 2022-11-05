package com.erdemklync.shopin.domain.use_cases.cart

import com.erdemklync.shopin.util.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

class ClearCart @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
) {
    operator fun invoke() {
        firebaseAuth.currentUser?.let { user ->
            firestore
                .collection(Constants.USERS)
                .document(user.uid)
                .collection(Constants.CART)
                .get()
                .addOnSuccessListener { snapshot ->
                    snapshot.forEach { docs ->
                        docs.reference.delete()
                    }
                }
        }
    }
}
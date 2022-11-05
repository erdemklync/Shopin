package com.erdemklync.shopin.domain.use_cases.cart

import com.erdemklync.shopin.data.remote.entity.Product
import com.erdemklync.shopin.util.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

class IncreaseAmount @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
) {
    operator fun invoke(
        product: Product,
    ) {
        firebaseAuth.currentUser?.let { user ->
            firestore
                .collection(Constants.USERS)
                .document(user.uid)
                .collection(Constants.CART)
                .document(product.id.toString())
                .update(Constants.FIELD_PRODUCT_AMOUNT, FieldValue.increment(1))
        }
    }
}